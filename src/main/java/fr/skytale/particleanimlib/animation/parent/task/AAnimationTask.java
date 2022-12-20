package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.*;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.collision.CollisionTestType;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {

    public static final Vector U = new Vector(1, 0, 0);
    public static final Vector V = new Vector(0, 0, 1);
    public static final Vector W = new Vector(0, 1, 0);

    protected T animation;
    protected AAnimationTask<?> parentTask;

    // Animation task main data management
    protected Integer taskId;
    protected int iterationCount;
    protected int currentShowPeriod;
    protected Location currentIterationBaseLocation;

    // Animation points management
    protected Set<AnimationTaskTrackedField<?>> trackedFieldsData;
    protected ForceUpdatePointsConfiguration forceUpdatePointsConfiguration;
    protected List<AnimationPointData> animationPoints;

    // Animation rotation management
    protected PARotation currentRotation;
    protected List<AnimationPointData> rotatedAnimationPoints;
    protected Vector currentU;
    protected Vector currentV;
    protected Vector currentW;

    protected AAnimationTask(T animation) {
        //noinspection unchecked
        this.animation = (T) animation.clone();
        if (this.animation.getPosition().getType() == APosition.Type.TRAIL) {
            throw new IllegalStateException("During execution, position type should not be TRAIL anymore");
        }
        this.iterationCount = 0;
        buildAnnotationData();
    }

    /* ******** OVERRIDABLE METHODS *********/

    protected abstract List<AnimationPointData> computeAnimationPoints();

    protected boolean shouldUpdatePoints() {
        return false;
    }

    protected boolean shouldStop() {
        return hasDurationEnded() || (iterationCount > 0 && this.animation.getStopCondition() != null && this.animation.getStopCondition().canStop(this));
    }

    /* ******** GETTERS AND SETTERS *********/

    protected final void startTask() {
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
        if (shouldRecomputePoints()) {
            animationPoints = computeAnimationPoints();
        }

        IVariable.ChangeResult<PARotation> rotationChangeResult = animation.getRotation().willChange(iterationCount, currentRotation);

        // Rotating animation if needed
        if (rotationChangeResult.hasChanged()) {
            currentRotation = rotationChangeResult.getNewValue();
            rotatedAnimationPoints = animationPoints.stream()
                    .map(pointData -> new AnimationPointData(currentRotation.rotateVector(pointData.getFromCenterToPoint()), pointData.getPointDefinitionModifier()))
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
    public final void stopAnimation() {
        stopAnimation(true);
    }

    public final void stopAnimation(boolean runCallback) {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
            if (runCallback && !animation.getCallbacks().isEmpty()) {
                animation.getCallbacks().forEach(animationEndedCallback -> animationEndedCallback.run(animation));
            }
        }
    }

    protected final void showPoint(APointDefinition pointDefinition, Location pointLocation, Location centerLocation) {
        showPoint(pointDefinition, pointLocation, pointLocation.toVector().subtract(centerLocation.toVector()));
    }

    protected final void showPoint(APointDefinition pointDefinition, Location pointLocation, Vector pointDirection) {
        if (pointDefinition.getShowMethodParameters() == APointDefinition.ShowMethodParameters.LOCATION) {
            pointDefinition.show(animation, pointLocation, this);
        } else {
            pointDefinition.show(animation, pointLocation, pointDirection, this);
        }

        this.animation.getCollisionHandlers().forEach(collisionHandler -> {
            collisionHandler.processCollision(iterationCount, CollisionTestType.PER_PARTICLE, pointLocation, this);
        });

    }

    private boolean shouldRecomputePoints() {

        boolean shouldRecomputePoints = false;
        for (AnimationTaskTrackedField<?> animationTaskTrackedField : trackedFieldsData) {
            //Updates the field value according to the current IVariableValue
            boolean currentValueChanged = animationTaskTrackedField.checkIfChangedAndUpdate(iterationCount);

            if (currentValueChanged
                    && this.forceUpdatePointsConfiguration.ifIVariableCurrentValueFieldsChanges()
                    && animationTaskTrackedField.getTaskIVarFieldAnnotation().updatePointsIfChange()) {
                shouldRecomputePoints = true;
            }
        }
        if (shouldRecomputePoints) {
            return true;
        }

        if (this.forceUpdatePointsConfiguration.alwaysUpdate()) {
            return true;
        }

        if (this.forceUpdatePointsConfiguration.ifShouldUpdatePointsMethod() && shouldUpdatePoints()) {
            return true;
        }

        return shouldRecomputePoints;
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

    private final void show(Location iterationBaseLocation) {

        rotatedAnimationPoints.forEach(animationPointData -> {
            showPoint(
                    animationPointData.applyModifier(animation.getPointDefinition()),
                    new Location(
                            iterationBaseLocation.getWorld(),
                            iterationBaseLocation.getX() + animationPointData.getFromCenterToPoint().getX(),
                            iterationBaseLocation.getY() + animationPointData.getFromCenterToPoint().getY(),
                            iterationBaseLocation.getZ() + animationPointData.getFromCenterToPoint().getZ()
                    ),
                    iterationBaseLocation);
        });
    }

    private void buildAnnotationData() {
        this.forceUpdatePointsConfiguration = this.getClass().getAnnotation(ForceUpdatePointsConfiguration.class);

        final Field[] animFields = animation.getClass().getFields();
        final Field[] taskFields = this.getClass().getFields();

        for (Field taskField : taskFields) {
            //Find fields having the @IVariableCurrentValue annotation
            //they will always contain the updated value of the IVariable inside the Task instance
            IVariableCurrentValue iVariableCurrentValueAnnotation = taskField.getAnnotation(IVariableCurrentValue.class);
            if (iVariableCurrentValueAnnotation != null) {
                // Allow overriding the related animation field name in the IVariableCurrentValue annotation
                // Else takes the same field name.
                String animFieldName = iVariableCurrentValueAnnotation.animationIVariableFieldName();
                if (animFieldName == null || animFieldName.equals("")) {
                    animFieldName = taskField.getName();
                }
                //Find the corresponding field in the AAnimation class
                boolean foundCorrespondingAnimField = false;
                for (Field animField : animFields) {
                    if (animField.getName().equals(animFieldName)) {
                        foundCorrespondingAnimField = true;
                        // The field must be an IVariable
                        if (!animField.getType().isInstance(IVariable.class)) {
                            throw new IllegalStateException("The field " + taskField.getName() + " of the AAnimationTask " + this.getClass().getSimpleName() + " references a field " + animFieldName + " of the AAnimation " + animation.getClass().getSimpleName() + ". This relation is not allowed because the AAnimation field is not an IVariable.");
                        }
                        // allow accessing the field
                        animField.setAccessible(true);
                        taskField.setAccessible(true);
                        try {
                            this.trackedFieldsData.add(AnimationTaskTrackedField.getFrom((IVariable<?>) animField.get(animation), this, taskField, iVariableCurrentValueAnnotation));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //If the field does not exist in the AAnimation class
                if (!foundCorrespondingAnimField) {
                    throw new IllegalStateException("The field " + taskField.getName() + " of the AAnimationTask " + this.getClass().getSimpleName() + " references a field " + animFieldName + " of the AAnimation " + animation.getClass().getSimpleName() + ". This relation is not allowed because the AAnimation field does not exist.");
                }
            }
        }
    }

    /* ******** GETTERS *********/

    public final Vector getCurrentU() {
        return currentU;
    }

    public final Vector getCurrentV() {
        return currentV;
    }

    public final Vector getCurrentW() {
        return currentW;
    }

    public final Vector getCurrentAbsoluteU() {
        return currentIterationBaseLocation.toVector().add(currentU);
    }

    public final Vector getCurrentAbsoluteV() {
        return currentIterationBaseLocation.toVector().add(currentV);
    }

    public final Vector getCurrentAbsoluteW() {
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
        return animation.getTicksDuration();
    }

    public final int getCurrentShowPeriod() {
        return currentShowPeriod;
    }

    public final AAnimationTask<?> getParentTask() {
        return this.parentTask;
    }

    public final void setParentTask(AAnimationTask<?> parentTask) {
        this.parentTask = parentTask;
    }

}