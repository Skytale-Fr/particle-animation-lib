package fr.skytale.particleanimlib.animation.attribute.projectiledirection;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;

public class LocationAnimationDirection extends AnimationDirection {

    public LocationAnimationDirection(IVariable<Location> targetLocation, IVariable<Double> speed) {
        super(
                null,
                targetLocation,
                null,
                speed,
                Type.TARGET_LOCATION
        );
    }
}
