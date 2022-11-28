package fr.skytale.particleanimlib.animation.attribute.var.parent;

public interface IVariable<T> {
    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     *
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    T getCurrentValue(int iterationCount);

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    IVariable<T> copy();

    /**
     * Returns true if this variable will change over time
     *
     * @return true if this variable will change over time
     */
    boolean isConstant();

    /**
     * Returns true if this variable may change for this iteration count
     *
     * @param iterationCount the current iterationCount
     * @return true if this variable may change for this iteration count
     */
    boolean mayChange(int iterationCount);

    /**
     * Returns a ChangeResult containing :
     * - if the variable has changed for this iterationCount
     * - The new value of the variable
     *
     * @param iterationCount the current iterationCount
     * @param previousValue  the previous value
     * @return a ChangeResult letting one know if the value changed and the new value
     */
    default ChangeResult<T> willChange(int iterationCount, T previousValue) {
        ChangeResult<T> changeResult;

        if (previousValue == null || mayChange(iterationCount)) {
            changeResult = new ChangeResult<>(true, getCurrentValue(iterationCount));
        } else {
            changeResult = new ChangeResult<>(false, previousValue);
        }

        return changeResult;
    }

    class ChangeResult<T> {
        private final boolean hasChanged;
        private final T newValue;

        public ChangeResult(boolean hasChanged, T newValue) {
            this.hasChanged = hasChanged;
            this.newValue = newValue;
        }

        public boolean hasChanged() {
            return hasChanged;
        }

        public T getNewValue() {
            return newValue;
        }
    }
}
