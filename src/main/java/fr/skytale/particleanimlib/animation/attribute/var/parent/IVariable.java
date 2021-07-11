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
     * Returns true if this variable will change for this iteration count
     *
     * @param iterationCount the current iterationCount
     * @return true if this variable will change for this iteration count
     */
    boolean willChange(int iterationCount);
}
