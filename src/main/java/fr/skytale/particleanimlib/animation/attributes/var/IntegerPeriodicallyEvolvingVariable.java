package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.APeriodicallyEvolvingVariable;

public class IntegerPeriodicallyEvolvingVariable extends APeriodicallyEvolvingVariable<Integer> {

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue  the start value
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public IntegerPeriodicallyEvolvingVariable(Integer startValue, Integer changeValue) {
        super(startValue, changeValue);
    }

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue   the start value
     * @param changeValue  The value that will be added to the variable at the frequency defined by changePeriod
     * @param changePeriod the period (delay between two change)
     */
    public IntegerPeriodicallyEvolvingVariable(Integer startValue, Integer changeValue, int changePeriod) {
        super(startValue, changeValue, changePeriod);
    }

    /**
     * Returns the sum of currentValue and changeValue
     *
     * @param currentValue the current value
     * @param changeValue  the difference that will be applied on current value
     * @return the sum of currentValue and changeValue
     */
    @Override
    protected Integer add(Integer currentValue, Integer changeValue) {
        return currentValue + changeValue;
    }
}
