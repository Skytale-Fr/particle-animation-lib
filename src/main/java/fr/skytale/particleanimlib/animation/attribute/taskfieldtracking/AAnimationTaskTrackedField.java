package fr.skytale.particleanimlib.animation.attribute.taskfieldtracking;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;

import java.lang.reflect.Field;

public abstract class AAnimationTaskTrackedField<TASK_FIELD_TYPE, ANIMATION_VARIABLE_TYPE> {
    protected final Field taskField;
    protected final AAnimationTask<?> task;
    protected final ANIMATION_VARIABLE_TYPE animationVariable;
    protected final boolean updatePointsIfChange;
    protected TASK_FIELD_TYPE fieldPreviousValue = null;

    AAnimationTaskTrackedField(Field taskField, AAnimationTask<?> task, ANIMATION_VARIABLE_TYPE animationVariable, boolean updatePointsIfChange) {
        this.taskField = taskField;
        this.task = task;
        this.animationVariable = animationVariable;
        this.updatePointsIfChange = updatePointsIfChange;
        if (taskField == null) {
            throw new IllegalArgumentException("taskField should not be null (" + toString() + ").");
        }
        if (task == null) {
            throw new IllegalArgumentException("task should not be null (" + toString() + ").");
        }
    }

    public abstract boolean checkIfChangedAndUpdate(Location currentLocation, int iterationCount);

    public final boolean updatePointsIfChange() {
        return updatePointsIfChange;
    }

    @Override
    public String toString() {
        return "AAnimationTaskTrackedField{" +
               "taskField=" + taskField +
               ", task=" + task +
               ", animationVariable=" + animationVariable +
               ", updatePointsIfChange=" + updatePointsIfChange +
               ", fieldPreviousValue=" + fieldPreviousValue +
               '}';
    }
}
