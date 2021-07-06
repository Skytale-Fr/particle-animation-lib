package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.ParametrizedCallback;

public class CallbackVariable<T> implements IVariable<T> {

    /**
     * The callback
     */
    protected ParametrizedCallback<T> callback;

    /**
     * Construct a callback variable
     * @param callback the callback that will be able to return the current value
     */
    public CallbackVariable(ParametrizedCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Retrieves the callback able to return the current value of type T
     * @return the callback able to return the current value of type T
     */
    public ParametrizedCallback<T> getCallback() {
        return callback;
    }

    /**
     * Defines the callback able to return the current value of type T
     * @param callback the callback able to return the current value of type T
     */
    public void setCallback(ParametrizedCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Retrieves the current value
     * @param iterationCount given to the callback in order to compute the current value
     * @return the current value
     */
    @Override
    public T getCurrentValue(int iterationCount) {
        return callback.run(iterationCount);
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<T> copy() {
        return new CallbackVariable<T>(this.callback);
    }

    /**
     * Returns true if this variable will change over time
     *
     * @return true if this variable will change over time
     */
    @Override
    public boolean isConstant() {
        return false;
    }

    /**
     * Returns true if this variable will change for this iteration count
     *
     * @param iterationCount the current iterationCount
     * @return true if this variable will change for this iteration count
     */
    @Override
    public boolean willChange(int iterationCount) {
        return true;
    }
}
