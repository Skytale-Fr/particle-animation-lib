package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DirectionSubAnimPointDefinition extends SubAnimPointDefinition {

    protected IDirectionSubAnimation subAnimation;

    protected final double speed;

    public DirectionSubAnimPointDefinition(IDirectionSubAnimation subAnimation, double speed) {
        super(ShowMethodParameters.LOCATION_AND_DIRECTION);
        this.subAnimation = subAnimation;
        this.speed = speed;
    }

    @Override
    public ISubAnimation getSubAnimation() {
        return subAnimation;
    }

    public IDirectionSubAnimation getSubDirectionAnimation() {
        return subAnimation;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    @Deprecated
    public void show(Location loc) {
        throw new IllegalStateException("This method should never be called for DirectionSubAnimPointDefinition. show(Location, Vector) should be called instead.");
    }

    @Override
    public void show(Location loc, Vector fromCenterToPoint) {
        subAnimation.setPosition(APosition.fromLocation(loc));
        subAnimation.setDirection(AnimationDirection.fromMoveVector(fromCenterToPoint.normalize().multiply(speed)));
        subAnimation.show();

    }

    @Override
    public DirectionSubAnimPointDefinition clone() {
        DirectionSubAnimPointDefinition obj = (DirectionSubAnimPointDefinition) super.clone();
        obj.subAnimation = (IDirectionSubAnimation) subAnimation.clone();
        return obj;
    }
}
