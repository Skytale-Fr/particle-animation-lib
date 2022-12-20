package fr.skytale.particleanimlib.animation.attribute.taskfieldtracking;


import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;

import java.lang.reflect.Field;

public class AnimationTaskTrackedIVariable<TASK_FIELD_TYPE> extends AAnimationTaskTrackedField<TASK_FIELD_TYPE, IVariable<TASK_FIELD_TYPE>> {

    public AnimationTaskTrackedIVariable(Field taskField, AAnimationTask<?> task, IVariable<TASK_FIELD_TYPE> animationVariable, IVariableCurrentValue taskIVarFieldAnnotation) {
        super(taskField, task, animationVariable, taskIVarFieldAnnotation.updatePointsIfChange());
    }

    @Override
    public boolean checkIfChangedAndUpdate(Location currentLocation, int iterationCount) {
        if (animationVariable == null) {
            try {
                taskField.set(task, null);
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        IVariable.ChangeResult<TASK_FIELD_TYPE> changeResult = animationVariable.willChange(iterationCount, fieldPreviousValue);
        fieldPreviousValue = changeResult.getNewValue();
        try {
            taskField.set(task, fieldPreviousValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return changeResult.hasChanged();
    }
}