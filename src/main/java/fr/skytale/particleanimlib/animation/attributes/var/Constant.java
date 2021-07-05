package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;

public class Constant<T> implements IVariable<T> {

    /**
     * The constant value
     */
    protected T value;

    /**
     * Defines the constant
     * @param value the constant
     */
    public Constant(T value) {
        this.value = value;
    }

    /**
     * Defines the constant value
     * @param value the constant value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Retrieves the constant value
     * @return the constant value
     */
    public T getValue() {
        return value;
    }

    /**
     * Retrieves the constant value
     * @param iterationCount unused because the value is a constant
     * @return the constant value
     */
    @Override
    public T getCurrentValue(int iterationCount) {
        return value;
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<T> copy() {
        return new Constant<T>(this.getValue());
    }

    /**
     * Returns true if this variable will change over time
     *
     * @return true if this variable will change over time
     */
    @Override
    public boolean isConstant() {
        return true;
    }

    /**
     * Returns true if this variable will change for this iteration count
     *
     * @param iterationCount the current iterationCount
     * @return true if this variable will change for this iteration count
     */
    @Override
    public boolean willChange(int iterationCount) {
        return false;
    }
}
