package fr.skytale.particleanimlib.animation.attributes.position;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.Location;

public class LocationPosition extends APosition {

    public LocationPosition(IVariable<Location> location) {
        this.type = Type.LOCATION;
        this.location = location;
    }
}
