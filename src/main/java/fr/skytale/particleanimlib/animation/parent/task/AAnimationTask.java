package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.collision.CollisionTestType;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.IVariableCurrentValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {

    public static final Vector U = new Vector(1, 0, 0);
    public static final Vector V = new Vector(0, 0, 1);
    public static final Vector W = new Vector(0, 1, 0);
    protected AAnimationTask<?> parentTask;
    protected T animation;
    protected Set<AnimationTaskTrackedField<?>> trackedFieldsData;
    //Evolving variables
    protected Integer taskId;
    protected int iterationCount;
    protected Location currentIterationBaseLocation;
    protected int tickDuration;
    protected int currentShowPeriod;
    protected PARotation currentRotation;
    protected Vector currentU;
    protected Vector currentV;
    protected Vector currentW;
    protected List<Vector> animationPoints;
    protected List<Vector> rotatedAnimationPoints;

    public AAnimationTask(T animation) {
        //noinspection unchecked
        this.animation = (T) animation.clone();
        if (this.animation.getPosition().getType() == APosition.Type.TRAIL) {
            throw new IllegalStateException("During execution, position type should not be TRAIL anymore");
        }
        this.iterationCount = 0;
        buildFieldsData();
    }

    public static Vector computeRadiusVector(Vector normalVector, double radius) {
        /*Let directorVector=(a,b,c).
        Then the equation of the plane containing the point (0,0,0) with directorVector as normal vector is ax + by + cz = 0.
        We want to find the vector radiusVector belonging to the plane*/
        double a = normalVector.getX();
        double b = normalVector.getY();
        double c = normalVector.getZ();

        Vector radiusVector;

        if (a == 0) {
            if (b == 0)
                radiusVector = new Vector(1, 1, 0);
            else if (c == 0)
                radiusVector = new Vector(1, 0, 1);
            else
                radiusVector = new Vector(1, 1, -b / c);
        } else if (b == 0) {
            if (c == 0)
                radiusVector = new Vector(0, 1, 1);
            else
                radiusVector = new Vector(1, 1, -a / c);
        } else if (c == 0)
            radiusVector = new Vector(1, -b / a, 1);
        else
            radiusVector = new Vector(1, 1, (-a - b) / c);

        return radiusVector.normalize().multiply(radius);
    }

    protected abstract List<Vector> computeAnimationPoints();

    protected final void startTask() {
        tickDuration = animation.getTicksDuration();
        this.taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(animation.getPlugin(), this, 0, 0).getTaskId();
    }

    public final void run() {
        computeIterationBaseLocation();

        //We only show at the specified frequency
        currentShowPeriod = animation.getShowPeriod().getCurrentValue(iterationCount);

        // If a stop condition has been set, we need to check this condition
        // and stop the animation if true is returned.
        if (shouldStop()) {
            stopAnimation(true);
            return;
        }

        // Recomputing animationPoints if needed
        if (hasAnimationPointsChanged()) {
            animationPoints = computeAnimationPoints();
        }

        IVariable.ChangeResult<PARotation> rotationChangeResult = animation.getRotation().willChange(iterationCount, currentRotation);

        // Rotating animation if needed
        if (rotationChangeResult.hasChanged()) {
            currentRotation = rotationChangeResult.getNewValue();
            rotatedAnimationPoints = animationPoints.stream()
                    .map(pointVector -> currentRotation.rotateVector(pointVector))
                    .collect(Collectors.toList());
            //Computing rotated 3D hyperplan(frame) from default hyperplan(minecraft world frame)
            currentU = currentRotation.rotateVector(U);
            currentV = currentRotation.rotateVector(V);
            currentW = currentRotation.rotateVector(W);
        }
        // Collecting potential targets (usually entities) that can be collided
        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.collect(iterationCount, this);
        });

        // Showing animation at given show period
        if (currentShowPeriod == 0 || iterationCount % currentShowPeriod == 0) {
            show(currentIterationBaseLocation);
        }

        // Processing collision
        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.processCollision(iterationCount, CollisionTestType.MAIN_PARTICLE, currentIterationBaseLocation, this);
        });

        iterationCount++;
    }

    private boolean shouldStop() {
        return hasDurationEnded() || (iterationCount > 0 && this.animation.getStopCondition() != null && this.animation.getStopCondition().canStop(this));
    }

    private void computeIterationBaseLocation() {
        APosition currentPosition = animation.getPosition();

        //Computing current animation location
        if (currentPosition.getType() == APosition.Type.ENTITY) {
            currentIterationBaseLocation = currentPosition.getMovingEntity().getLocation().clone()
                    .add(currentPosition.getRelativeLocation().getCurrentValue(iterationCount).clone());
        } else {
            currentIterationBaseLocation = currentPosition.getLocation().getCurrentValue(iterationCount).clone();
        }
    }

    protected final void show(Location iterationBaseLocation) {

        rotatedAnimationPoints.stream()
                // Computing each point location according to iterationBaseLocation
                .map(rotatedAnimationPoint -> new Location(
                                iterationBaseLocation.getWorld(),
                                iterationBaseLocation.getX() + rotatedAnimationPoint.getX(),
                                iterationBaseLocation.getY() + rotatedAnimationPoint.getY(),
                                iterationBaseLocation.getZ() + rotatedAnimationPoint.getZ()
                        )
                )
                // Showing each point
                .forEach(pointLocation -> showPoint(
                        animation.getPointDefinition(),
                        pointLocation,
                        iterationBaseLocation));
    }

    /********* GETTERS AND SETTERS *********/

    public final Vector getCurrentU() {
        return currentU;
    }

    public final Vector getCurrentV() {
        return currentV;
    }

    public final Vector getCurrentW() {
        return currentW;
    }

    public Vector getCurrentAbsoluteU() {
        return currentIterationBaseLocation.toVector().add(currentU);
    }

    public Vector getCurrentAbsoluteV() {
        return currentIterationBaseLocation.toVector().add(currentV);
    }

    public Vector getCurrentAbsoluteW() {
        return currentIterationBaseLocation.toVector().add(currentW);
    }

    public final boolean hasDurationEnded() {
        return iterationCount >= animation.getTicksDuration();
    }

    public final int getIterationCount() {
        return iterationCount;
    }

    public final Location getCurrentIterationBaseLocation() {
        return currentIterationBaseLocation;
    }

    public final int getTickDuration() {
        return tickDuration;
    }

    public final int getCurrentShowPeriod() {
        return currentShowPeriod;
    }

    public final void stopAnimation() {
        stopAnimation(true);
    }

    public final AAnimationTask<?> getParentTask() {
        return this.parentTask;
    }

    public final void setParentTask(AAnimationTask<?> parentTask) {
        this.parentTask = parentTask;
    }

    protected void stopAnimation(boolean runCallback) {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
            if (runCallback && !animation.getCallbacks().isEmpty()) {
                animation.getCallbacks().forEach(animationEndedCallback -> animationEndedCallback.run(animation));
            }
        }
    }


    public List<Vector> getLinePoints(Vector point1, Vector point2, double step) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.subtract(point1).normalize().multiply(step);
        Vector currentLoc = point1.clone();

        List<Vector> linePoints = new ArrayList<>();
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            linePoints.add(currentLoc.clone());
            length += step;
        }
        linePoints.add(point2.clone());
        return linePoints;
    }

    public Location rotateAroundAxis(Location point, Vector axis, Location pointAxis, double angle) {
        Vector v = point.clone().subtract(pointAxis).toVector(); //Vecteur de axis au point à translater
        RotatableVector rotatableVector = new RotatableVector(v.getX(), v.getY(), v.getZ());
        v = rotatableVector.rotateAroundAxis(axis, angle);
        v = v.add(pointAxis.toVector()); //Translation vers le point d'origine (La rotiation a été faite à l'origine du repère)
        return v.toLocation(Objects.requireNonNull(point.getWorld()));
    }

    public void showPoint(APointDefinition pointDefinition, Location pointLocation, Location centerLocation) {
        showPoint(pointDefinition, pointLocation, pointLocation.toVector().subtract(centerLocation.toVector()));
    }

    public void showPoint(APointDefinition pointDefinition, Location pointLocation, Vector pointDirection) {
        if (pointDefinition.getShowMethodParameters() == APointDefinition.ShowMethodParameters.LOCATION) {
            pointDefinition.show(animation, pointLocation, this);
        } else {
            pointDefinition.show(animation, pointLocation, pointDirection, this);
        }

        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.processCollision(iterationCount, CollisionTestType.PER_PARTICLE, pointLocation, this);
        });

    }

    private boolean hasAnimationPointsChanged() {
        boolean hasChanged = false;

        for (AnimationTaskTrackedField<?> animationTaskTrackedField : trackedFieldsData) {
            if (animationTaskTrackedField.checkIfChangedAndUpdate(iterationCount)) {
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    private void buildFieldsData() {
        final Field[] animFields = animation.getClass().getFields();
        //Find fields that must always contain the updated value of the IVariable inside the Task instance
        for (Field taskField : this.getClass().getFields()) {
            IVariableCurrentValue iVariableCurrentValueAnnotation = taskField.getAnnotation(IVariableCurrentValue.class);
            if (iVariableCurrentValueAnnotation != null) {
                // Allow overriding the related animation field name in the IVariableCurrentValue annotation
                // Else takes the same field name.
                String animFieldName = iVariableCurrentValueAnnotation.animationIVariableFieldName();
                if (animFieldName == null || animFieldName.equals("")) {
                    animFieldName = taskField.getName();
                }
                boolean foundCorrespondingAnimField = false;
                for (Field animField : animFields) {
                    if (animField.getName().equals(animFieldName)) {
                        foundCorrespondingAnimField = true;
                        if (!animField.getType().isInstance(IVariable.class)) {
                            throw new IllegalStateException("The field " + taskField.getName() + " of the AAnimationTask " + this.getClass().getSimpleName() + " references a field " + animFieldName + " of the AAnimation " + animation.getClass().getSimpleName() + ". This relation is not allowed because the AAnimation field is not an IVariable.");
                        }
                        animField.setAccessible(true);
                        taskField.setAccessible(true);
                        try {
                            this.trackedFieldsData.add(AnimationTaskTrackedField.getFrom((IVariable<?>) animField.get(animation), this, taskField));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!foundCorrespondingAnimField) {
                    throw new IllegalStateException("The field " + taskField.getName() + " of the AAnimationTask " + this.getClass().getSimpleName() + " references a field " + animFieldName + " of the AAnimation " + animation.getClass().getSimpleName() + ". This relation is not allowed because the AAnimation field does not exist.");
                }
            }
        }
    }

    private static class AnimationTaskTrackedField<T> {
        public static <U> AnimationTaskTrackedField<U> getFrom(IVariable<U> iVariable, AAnimationTask<?> task, Field taskField) {
            return new AnimationTaskTrackedField<>(iVariable, task, taskField);
        }
        private final Field taskField;
        private final AAnimationTask<?> task;
        private final IVariable<T> iVariable;
        private T fieldPreviousValue = null;

        public AnimationTaskTrackedField(IVariable<T> iVariable, AAnimationTask<?> task, Field taskField) {
            this.iVariable = iVariable;
            this.task = task;
            this.taskField = taskField;
        }

        public boolean checkIfChangedAndUpdate(int iterationCount) {
            IVariable.ChangeResult<T> changeResult = iVariable.willChange(iterationCount, fieldPreviousValue);
            fieldPreviousValue = changeResult.getNewValue();
            try {
                taskField.set(task, fieldPreviousValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return changeResult.hasChanged();
        }
    }

}