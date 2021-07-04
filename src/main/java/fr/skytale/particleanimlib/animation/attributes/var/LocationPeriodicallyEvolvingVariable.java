package fr.skytale.particleanimlib.animation.attributes.var;

import fr.skytale.particleanimlib.animation.attributes.var.parent.APeriodicallyEvolvingVariable;
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
}
