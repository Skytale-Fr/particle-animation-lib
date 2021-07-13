package fr.skytale.particleanimlib.animation.attribute.var;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.ParametrizedCallbackWithPreviousValue;

public class CallbackWithPreviousValueVariable<T> implements IVariable<T> {

    /**
     * The callback
     */
    protected ParametrizedCallbackWithPreviousValue<T> callback;

    /**
     * The previous value
     */
    protected T previousValue;

    /**
     * The previous value iterationCount
     * (used to avoid the value to be increased twice if getCurrentValue is called twice during the same iteration)
     */
    protected int previousIterationCount;

    /**
     * Construct a callback variable
     *
     * @param startValue the initial value
     * @param callback   the callback that will be able to return the current value
     */
    public CallbackWithPreviousValueVariable(T startValue, ParametrizedCallbackWithPreviousValue<T> callback) {
        this.previousValue = startValue;
        this.callback = callback;
        this.previousIterationCount = 0;
    }

    /**
     * Retrieves the callback able to return the current value of type T
     *
     * @return the callback able to return the current value of type T
     */
    public ParametrizedCallbackWithPreviousValue<T> getCallback() {
        return callback;
    }

    /**
     * Defines the callback able to return the current value of type T
     *
     * @param callback the callback able to return the current value of type T
     */
    public void setCallback(ParametrizedCallbackWithPreviousValue<T> callback) {
        this.callback = callback;
    }

    /**
     * Retrieves the current value
     *
     * @param iterationCount given to the callback in order to compute the current value
     * @return the current value
     */
    @Override
    public T getCurrentValue(int iterationCount) {
        if (iterationCount != previousIterationCount) {
            previousValue = callback.run(iterationCount, previousValue);
            previousIterationCount = iterationCount;
        }
        return previousValue;
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<T> copy() {
        return new CallbackWithPreviousValueVariable<T>(this.previousValue, this.callback);
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
