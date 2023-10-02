package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.taskfieldtracking.AAnimationTaskTrackedField;
import fr.skytale.particleanimlib.animation.attribute.taskfieldtracking.AnimationTaskTrackedIVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.collision.processor.CollisionTestType;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {

    public static final Vector U = new Vector(1, 0, 0);
    public static final Vector V = new Vector(0, 0, 1);
    public static final Vector W = new Vector(0, 1, 0);
    private final AAnimationPosition position;
    protected T animation;
    protected AAnimationTask<?> parentTask;
    // Animation task main data management
    protected Integer taskId;
    protected int iterationCount;
    protected int currentShowPeriod;
    protected AnimationMove currentIterationMove;
    // Animation points management
    protected Set<AAnimationTaskTrackedField<?, ?>> trackedFieldsData;
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
        if (this.animation.getPosition().getType() != PositionType.NORMAL) {
            throw new IllegalStateException("During execution, the type of the position of an animation must be NORMAL. Usually, this error occurs when a TrailPosition is called directly instead of being used in the context of the Trail system.");
        }
        this.position = (AAnimationPosition) this.animation.getPosition();
        this.iterationCount = 0;
        this.trackedFieldsData = new HashSet<>();
        buildAnnotationData();
    }

    /* ******** GETTERS *********/

    public final T getAnimation() {
        return animation;
    }

    public final int getIterationCount() {
        return iterationCount;
    }

    public final Location getCurrentIterationBaseLocation() {
        return currentIterationMove.getAfterMoveLocation();
    }

    public AnimationMove getCurrentIterationMove() {
        return currentIterationMove;
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

    public final boolean isRunning() {
        return taskId != null;
    }

    public final Vector getCurrentU() { return currentU; }

    public final Vector getCurrentV() {
        return currentV;
    }

    public final Vector getCurrentW() {
        return currentW;
    }

    /* ******** SETTER *********/

    public final void setParentTask(AAnimationTask<?> parentTask) {
        this.parentTask = parentTask;
    }

    /* ******** OTHER PUBLIC METHODS *********/
    @Override
    public final void run() {

        //We only show at the specified frequency
        currentShowPeriod = animation.getShowPeriod().getCurrentValue(iterationCount);

        final boolean isIterationShown = currentShowPeriod == 0 || iterationCount % currentShowPeriod == 0;

        //Compute the move (including the new animation location)
        currentIterationMove = position.getCurrentValue(iterationCount);

        // If a stop condition has been set, we need to check this condition
        // and stop the animation if true is returned.
        if (shouldStop()) {
            //If the move was cancelled we will not run the callback
            //It usually occurs when the animation is moving towards an entity and if:
            // - the entity is dead
            // - or the entity was teleported to another world
            // - or the entity (player) left the server
            stopAnimation(!getCurrentIterationMove().isCancelled());
            return;
        }

        // Recomputing animationPoints if needed
        boolean animationPointsChanged = false;

        if (isIterationShown && shouldRecomputePoints()) {
            animationPoints = computeAnimationPoints();
            animationPointsChanged = true;
        }

        if (animationPoints != null && !animationPoints.isEmpty()) {

            // Compute new rotation
            IVariable.ChangeResult<PARotation> rotationChangeResult = animation.getRotation().willChange(iterationCount, currentRotation);
            final boolean rotationChanged = rotationChangeResult.hasChanged();
            if (rotationChanged) {
                currentRotation = rotationChangeResult.getNewValue();
                //Computing rotated 3D hyperplan(frame) from default hyperplan(minecraft world frame)
                currentU = currentRotation.rotateVector(U);
                currentV = currentRotation.rotateVector(V);
                currentW = currentRotation.rotateVector(W);
            }

            // (re)compute rotated points
            if (rotationChanged || animationPointsChanged) {
                rotatedAnimationPoints = animationPoints.stream()
                        .map(pointData -> new AnimationPointData(
                                currentRotation.rotateVector(pointData.getFromCenterToPoint().clone()), pointData.getPointDefinitionModifier()))
                        .collect(Collectors.toList());
            }

            // Collecting potential targets (usually entities) that can be collided
            this.animation.getCollisionHandlers().forEach(collisionHandler ->
                    collisionHandler.collect(iterationCount, this));

            // Showing animation at given show period
            if (isIterationShown) {
                show();
            }

            // Processing collision
            this.animation.getCollisionHandlers().forEach(collisionHandler ->
                    collisionHandler.processCollision(iterationCount, CollisionTestType.MAIN_PARTICLE, currentIterationMove.getAfterMoveLocation(), this));
        }
        if (currentIterationMove.hasReachedTarget()) {
            stopAnimation();
        }

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
                animation.getCallbacks().forEach(animationEndedCallback -> animationEndedCallback.run(this));
            }
        }
    }

    /* ******** OVERRIDABLE METHODS *********/

    protected abstract List<AnimationPointData> computeAnimationPoints();

    protected boolean shouldUpdatePoints() {
        return false;
    }

    protected boolean shouldStop() {
        return getCurrentIterationMove().isCancelled() ||
               hasDurationEnded() ||
               (
                       iterationCount > 0 &&
                       this.animation.getStopCondition() != null &&
                       this.animation.getStopCondition().canStop((AAnimationTask<?>) this)
               );
    }

    /* ******** OTHER PROTECTED METHODS *********/

    protected final void startTask() {
        this.taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(animation.getPlugin(), this, 0, 0).getTaskId();
    }

    protected final void showPoint(APointDefinition pointDefinition, Location pointLocation) {
        pointDefinition.show(
                pointLocation,
                animation,
                this,
                pointLocation.toVector().subtract(getCurrentIterationBaseLocation().toVector()),
                getCurrentIterationMove().getMove());

        this.animation.getCollisionHandlers().forEach(collisionHandler ->
                collisionHandler.processCollision(iterationCount, CollisionTestType.PER_PARTICLE, pointLocation, this));

    }

    /* ******** PRIVATE METHODS *********/

    private boolean hasDurationEnded() {
        return iterationCount >= animation.getTicksDuration();
    }

    private boolean shouldRecomputePoints() {

        boolean shouldRecomputePoints = false;
        for (AAnimationTaskTrackedField<?, ?> tackedFieldData : trackedFieldsData) {
            //Updates the field value according to the current IVariableValue
            boolean currentValueChanged = tackedFieldData.checkIfChangedAndUpdate(getCurrentIterationBaseLocation(), iterationCount);

            if (currentValueChanged
                && (
                        this.forceUpdatePointsConfiguration == null ||
                        this.forceUpdatePointsConfiguration.ifIVariableCurrentValueFieldsChanges()
                )
                && tackedFieldData.updatePointsIfChange()) {
                shouldRecomputePoints = true;
            }
        }

        return
                // If a IVariable value changed and has been defined, through its annotation, to update points on change.
                shouldRecomputePoints ||

                // If this is the first iteration
                animationPoints == null ||

                // If the AnimationTask has been defined to always update point through its annotation
                (this.forceUpdatePointsConfiguration != null && this.forceUpdatePointsConfiguration.alwaysUpdate()) ||

                // If the shouldUpdatePoint method should be called (according to annotation)
                // and if the result of this method is true
                (
                        (
                                this.forceUpdatePointsConfiguration == null ||
                                this.forceUpdatePointsConfiguration.ifShouldUpdatePointsMethod()
                        ) &&
                        shouldUpdatePoints()
                );
    }

    private void show() {

        rotatedAnimationPoints.forEach(animationPointData ->
                showPoint(
                        animationPointData.applyModifier(animation.getPointDefinition()),
                        new Location(
                                getCurrentIterationBaseLocation().getWorld(),
                                getCurrentIterationBaseLocation().getX() +
                                animationPointData.getFromCenterToPoint().getX(),
                                getCurrentIterationBaseLocation().getY() +
                                animationPointData.getFromCenterToPoint().getY(),
                                getCurrentIterationBaseLocation().getZ() +
                                animationPointData.getFromCenterToPoint().getZ()
                        )));

    }

    private void buildAnnotationData() {
        this.forceUpdatePointsConfiguration = this.getClass().getAnnotation(ForceUpdatePointsConfiguration.class);

        final Set<Field> animFields = new HashSet<>();
        final Set<Field> taskFields = new HashSet<>();

        //retrieve animation class fields
        for (Class<?> animClass = animation.getClass(); animClass != null; animClass = animClass.getSuperclass()) {
            animFields.addAll(Arrays.asList(animClass.getDeclaredFields()));
        }

        //retrieve animation class fields
        for (Class<?> taskClass = this.getClass(); taskClass != null; taskClass = taskClass.getSuperclass()) {
            taskFields.addAll(Arrays.asList(taskClass.getDeclaredFields()));
        }

        for (Field taskField : taskFields) {
            //Find fields having the @IVariableCurrentValue annotation
            //they will always contain the updated value of the IVariable inside the Task instance
            buildIVariableAnnotationData(animFields, taskField);
        }
    }

    private void buildIVariableAnnotationData(Set<Field> animFields, Field taskField) {
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
                    // allow accessing the field
                    animField.setAccessible(true);
                    taskField.setAccessible(true);
                    // The field must be an IVariable

                    try {
                        if (animField.getType().equals(IVariable.class)) {
                            this.trackedFieldsData.add(new AnimationTaskTrackedIVariable<>(taskField, this, (IVariable<?>) animField.get(animation), iVariableCurrentValueAnnotation));
                        } else {
                            throw new IllegalStateException(
                                    "The field " + taskField.getName() + " of the AAnimationTask " +
                                    this.getClass().getSimpleName() + " references a field " + animFieldName +
                                    " of the AAnimation " + animation.getClass().getSimpleName() +
                                    ". This relation is not allowed because the AAnimation field is not an IVariable or an IVariableLocated.");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            //If the field does not exist in the AAnimation class
            if (!foundCorrespondingAnimField) {
                throw new IllegalStateException("The field " + taskField.getName() + " of the AAnimationTask " +
                                                this.getClass().getSimpleName() + " references a field " +
                                                animFieldName + " of the AAnimation " +
                                                animation.getClass().getSimpleName() +
                                                ". This relation is not allowed because the AAnimation field does not exist.");
            }
        }
    }

}