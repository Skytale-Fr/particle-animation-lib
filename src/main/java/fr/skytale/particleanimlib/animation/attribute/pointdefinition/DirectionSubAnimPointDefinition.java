package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DirectionSubAnimPointDefinition extends SubAnimPointDefinition {

    protected final double speed;
    protected Vector direction;
    protected final boolean directionIsRelative;
    protected IDirectionSubAnimation subAnimation;

    public DirectionSubAnimPointDefinition(IDirectionSubAnimation subAnimation, double speed, Vector direction, boolean directionIsRelative) {
        super(ShowMethodParameters.LOCATION_AND_DIRECTION);
        this.subAnimation = subAnimation;
        this.speed = speed;
        this.direction = direction.clone().normalize();
        this.directionIsRelative = directionIsRelative;
    }

    public DirectionSubAnimPointDefinition(IDirectionSubAnimation subAnimation, double speed) {
        this(subAnimation, speed, new Vector(0,0,0), true);
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
        if (directionIsRelative) throw new IllegalStateException("This method should never be called for DirectionSubAnimPointDefinition if isDirectionRelative is true. Set a fixed direction instead or call show(Location, Vector) instead.");
        IDirectionSubAnimation newSubAnimation = (IDirectionSubAnimation) subAnimation.clone();
        newSubAnimation.setPosition(APosition.fromLocation(loc));
        newSubAnimation.setDirection(AnimationDirection.fromMoveVector(direction.clone().multiply(speed)));
        newSubAnimation.show();
    }

    @Override
    public void show(Location loc, Vector fromCenterToPoint) {
        IDirectionSubAnimation newSubAnimation = (IDirectionSubAnimation) subAnimation.clone();
        newSubAnimation.setPosition(APosition.fromLocation(loc));
        AnimationDirection subAnimDirection;
        if (directionIsRelative) {
            subAnimDirection = AnimationDirection.fromMoveVector(
                    fromCenterToPoint.normalize().add(direction).normalize().multiply(speed)
            );
        } else {
            subAnimDirection = AnimationDirection.fromMoveVector(direction.clone().multiply(speed));
        }
        newSubAnimation.setDirection(subAnimDirection);
        newSubAnimation.show();
    }

    @Override
    public DirectionSubAnimPointDefinition clone() {
        DirectionSubAnimPointDefinition obj = (DirectionSubAnimPointDefinition) super.clone();
        obj.subAnimation = (IDirectionSubAnimation) subAnimation.clone();
        obj.direction = direction.clone();
        return obj;
    }
}
