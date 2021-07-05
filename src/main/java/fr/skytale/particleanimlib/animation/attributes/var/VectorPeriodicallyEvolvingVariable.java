package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.APeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.util.Vector;

public class VectorPeriodicallyEvolvingVariable extends APeriodicallyEvolvingVariable<Vector> {
    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue  the start value
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public VectorPeriodicallyEvolvingVariable(Vector startValue, Vector changeValue) {
        super(startValue, changeValue);
    }

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue   the start value
     * @param changeValue  The value that will be added to the variable at the frequency defined by changePeriod
     * @param changePeriod the period (delay between two change)
     */
    public VectorPeriodicallyEvolvingVariable(Vector startValue, Vector changeValue, int changePeriod) {
        super(startValue, changeValue, changePeriod);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param vectorPeriodicallyEvolvingVariable another VectorPeriodicallyEvolvingVariable
     */
    public VectorPeriodicallyEvolvingVariable(VectorPeriodicallyEvolvingVariable vectorPeriodicallyEvolvingVariable) {
        super(vectorPeriodicallyEvolvingVariable);
    }

    /**
     * Returns the sum of currentValue and changeValue
     *
     * @param currentValue the current value
     * @param changeValue  the difference that will be applied on current value
     * @return the sum of currentValue and changeValue
     */
    @Override
    protected Vector add(Vector currentValue, Vector changeValue) {
        return currentValue.add(changeValue);
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Vector> copy() {
        return new VectorPeriodicallyEvolvingVariable(this);
    }
}
