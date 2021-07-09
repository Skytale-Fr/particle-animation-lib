package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationSubAnimPointDefinition extends SubAnimPointDefinition {

    protected ISubAnimation subAnimation;

    public LocationSubAnimPointDefinition(ISubAnimation subAnimation) {
        super(ShowMethodParameters.LOCATION);
        this.subAnimation = subAnimation;
    }

    @Override
    public ISubAnimation getSubAnimation() {
        return subAnimation;
    }

    public ISubAnimation getSubDirectionAnimation() {
        return subAnimation;
    }

    @Override
    @Deprecated
    public void show(Location loc) {
        subAnimation.setPosition(APosition.fromLocation(loc));
        subAnimation.show();
    }

    @Override
    public void show(Location loc, Vector fromCenterToPoint) {
        throw new IllegalStateException("This method should never be called for DirectionSubAnimPointDefinition. show(Location, Vector) should be called instead.");
    }

    @Override
    public LocationSubAnimPointDefinition clone() {
        LocationSubAnimPointDefinition obj = (LocationSubAnimPointDefinition) super.clone();
        obj.subAnimation = (ISubAnimation) subAnimation.clone();
        return obj;
    }
}
