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
}
