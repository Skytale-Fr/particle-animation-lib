package fr.skytale.particleanimlib.animation.attribute.var;

import fr.skytale.particleanimlib.animation.attribute.var.parent.APeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

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
     * Create a new Evolving variable by copy
     *
     * @param integerPeriodicallyEvolvingVariable another IntegerPeriodicallyEvolvingVariable
     */
    public IntegerPeriodicallyEvolvingVariable(IntegerPeriodicallyEvolvingVariable integerPeriodicallyEvolvingVariable) {
        super(integerPeriodicallyEvolvingVariable);
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

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Integer> copy() {
        return new IntegerPeriodicallyEvolvingVariable(this);
    }
}
