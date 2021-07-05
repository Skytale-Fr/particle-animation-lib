package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.APeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.Location;

public class LocationPeriodicallyEvolvingVariable extends APeriodicallyEvolvingVariable<Location> {

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue  the start value
     * @param changeValue the difference that will be applied to the variable at the frequency defined by changePeriod
     */
    public LocationPeriodicallyEvolvingVariable(Location startValue, Location changeValue) {
        super(startValue, changeValue);
    }

    /**
     * Create a new Periodically Evolving variable
     *
     * @param startValue   the start value
     * @param changeValue  The value that will be added to the variable at the frequency defined by changePeriod
     * @param changePeriod the period (delay between two change)
     */
    public LocationPeriodicallyEvolvingVariable(Location startValue, Location changeValue, int changePeriod) {
        super(startValue, changeValue, changePeriod);
    }

    /**
     * Create a new Evolving variable by copy
     *
     * @param locationPeriodicallyEvolvingVariable another LocationPeriodicallyEvolvingVariable
     */
    public LocationPeriodicallyEvolvingVariable(LocationPeriodicallyEvolvingVariable locationPeriodicallyEvolvingVariable) {
        super(locationPeriodicallyEvolvingVariable);
    }

    /**
     * Returns the sum of currentValue and changeValue
     *
     * @param currentValue the current value
     * @param changeValue  the difference that will be applied on current value
     * @return the sum of currentValue and changeValue
     */
    @Override
    protected Location add(Location currentValue, Location changeValue) {
        return currentValue.add(changeValue);
    }

    /**
     * Clone a IVariable
     *
     * @return the cloned IVariable
     */
    @Override
    public IVariable<Location> copy() {
        return new LocationPeriodicallyEvolvingVariable(this);
    }
}
