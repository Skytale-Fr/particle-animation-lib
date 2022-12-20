package fr.skytale.particleanimlib.animation.attribute;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.lang.reflect.Field;

public class AnimationTaskTrackedField<T> {
    private final Field taskField;
    private final AAnimationTask<?> task;
    private final IVariable<T> iVariable;
    private final IVariableCurrentValue taskIVarFieldAnnotation;
    private T fieldPreviousValue = null;

    public AnimationTaskTrackedField(IVariable<T> iVariable, AAnimationTask<?> task, Field taskField, IVariableCurrentValue taskIVarFieldAnnotation) {
        this.iVariable = iVariable;
        this.task = task;
        this.taskField = taskField;
        this.taskIVarFieldAnnotation = taskIVarFieldAnnotation;
    }

    public static <U> AnimationTaskTrackedField<U> getFrom(IVariable<U> iVariable, AAnimationTask<?> task, Field taskField, IVariableCurrentValue iVariableCurrentValueAnnotation) {
        return new AnimationTaskTrackedField<>(iVariable, task, taskField, iVariableCurrentValueAnnotation);
    }

    public boolean checkIfChangedAndUpdate(int iterationCount) {
        if (iVariable == null) {
            try {
                taskField.set(task, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        IVariable.ChangeResult<T> changeResult = iVariable.willChange(iterationCount, fieldPreviousValue);
        fieldPreviousValue = changeResult.getNewValue();
        try {
            taskField.set(task, fieldPreviousValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return changeResult.hasChanged();
    }

    public IVariableCurrentValue getTaskIVarFieldAnnotation() {
        return taskIVarFieldAnnotation;
    }
}