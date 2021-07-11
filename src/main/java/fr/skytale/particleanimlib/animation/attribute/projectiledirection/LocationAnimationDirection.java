package fr.skytale.particleanimlib.animation.attribute.projectiledirection;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
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

    public LocationAnimationDirection(Location targetLocation, IVariable<Double> speed) {
        this(new Constant<>(targetLocation), speed);
    }

    public LocationAnimationDirection(IVariable<Location> targetLocation, double speed) {
        this(targetLocation, new Constant<>(speed));
    }

    public LocationAnimationDirection(Location targetLocation, double speed) {
        this(new Constant<>(targetLocation), new Constant<>(speed));
    }

    @Override
    public LocationAnimationDirection clone() {
        return (LocationAnimationDirection) super.clone();
    }
}
