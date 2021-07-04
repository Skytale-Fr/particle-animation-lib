package fr.skytale.particleanimlib.animation.attributes.var.parent;

public abstract class APeriodicallyEvolvingVariable<T> implements IVariable<T> {

    /**
     * The starting value
     */
    protected T startValue;

    /**
     * The period (delay between two change)
     */
    protected int changePeriod;

    /**
     * The difference that will be applied to the variable at the frequency defined by changePeriod
     */
    protected T changeValue;

    /**
     * The local variable describing the current value (that will change over time)
     */
    protected T currentValue;

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue the start value
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public APeriodicallyEvolvingVariable(T startValue, T changeValue) {
        this(startValue, changeValue,0);
    }

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue the start value
     * @param changeValue The value that will be added to the variable at the frequency defined by changePeriod
     * @param changePeriod the period (delay between two change)
     */
    public APeriodicallyEvolvingVariable(T startValue, T changeValue, int changePeriod) {
        this.startValue = startValue;
        this.currentValue = startValue;
        this.changeValue = changeValue;
        this.changePeriod = changePeriod;
    }

    /**
     * Retrieves the value that will be used at the beginning
     * @return the start value
     */
    public final T getStartValue() {
        return startValue;
    }

    /**
     * Defines the value that will be used at the beginning
     * @param startValue the start value
     */
    public final void setStartValue(T startValue) {
        this.startValue = startValue;
    }

    /**
     * Retrieves the period (delay between two change)
     * @return the period (delay between two change)
     */
    public final int getChangePeriod() {
        return changePeriod;
    }

    /**
     * Defines the period (delay between two change)
     * @param changePeriod the period (delay between two change)
     */
    public final void setChangePeriod(int changePeriod) {
        this.changePeriod = changePeriod;
    }

    /**
     * Retrieves the difference that will be applied to the variable at the frequency defined by changePeriod
     * @return the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public final T getChangeValue() {
        return changeValue;
    }

    /**
     * Defines the difference that will be applied to the variable at the frequency defined by changePeriod
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public final void setChangeValue(T changeValue) {
        this.changeValue = changeValue;
    }

    @Override
    public final T getCurrentValue(int iterationCount) {
        if (iterationCount % changePeriod == 0) {
            currentValue = add(currentValue, changeValue);
        }
        return currentValue;
    }

    /**
     * Returns the sum of currentValue and changeValue
     * @param currentValue the current value
     * @param changeValue the difference that will be applied on current value
     * @return the sum of currentValue and changeValue
     */
    protected abstract T add(T currentValue, T changeValue);

}
