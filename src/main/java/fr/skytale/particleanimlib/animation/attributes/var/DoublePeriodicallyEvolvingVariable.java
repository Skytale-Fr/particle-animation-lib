package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.APeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;

public class DoublePeriodicallyEvolvingVariable extends APeriodicallyEvolvingVariable<Double> {

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue  the start value
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public DoublePeriodicallyEvolvingVariable(Double startValue, Double changeValue) {
        super(startValue, changeValue);
    }

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue   the start value
     * @param changeValue  The value that will be added to the variable at the frequency defined by changePeriod
     * @param changePeriod the period (delay between two change)
     */
    public DoublePeriodicallyEvolvingVariable(Double startValue, Double changeValue, int changePeriod) {
        super(startValue, changeValue, changePeriod);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param doublePeriodicallyEvolvingVariable another DoublePeriodicallyEvolvingVariable
     */
    protected DoublePeriodicallyEvolvingVariable(DoublePeriodicallyEvolvingVariable doublePeriodicallyEvolvingVariable) {
        super(doublePeriodicallyEvolvingVariable);
    }

    /**
     * Returns the sum of currentValue and changeValue
     *
     * @param currentValue the current value
     * @param changeValue  the difference that will be applied on current value
     * @return the sum of currentValue and changeValue
     */
    @Override
    protected Double add(Double currentValue, Double changeValue) {
        return currentValue + changeValue;
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Double> copy() {
        return new DoublePeriodicallyEvolvingVariable(this);
    }
}
