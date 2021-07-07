package fr.skytale.particleanimlib.animation.attribute.position;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;

public class LocationPosition extends APosition {

    public LocationPosition(IVariable<Location> location) {
        this.type = Type.LOCATION;
        this.location = location;
    }
}
