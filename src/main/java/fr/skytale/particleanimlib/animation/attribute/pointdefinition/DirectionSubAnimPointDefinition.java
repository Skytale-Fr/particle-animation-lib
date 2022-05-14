package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.DirectionVectorModifierCallback;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DirectionSubAnimPointDefinition extends SubAnimPointDefinition {

    protected final double speed;
    protected final DirectionVectorModifierCallback directionVectorModifierCallback;
    protected IDirectionSubAnimation subAnimation;

    public DirectionSubAnimPointDefinition(IDirectionSubAnimation subAnimation, double speed, DirectionVectorModifierCallback directionVectorModifierCallback) {
        super(ShowMethodParameters.LOCATION_AND_DIRECTION);
        this.subAnimation = subAnimation;
        this.speed = speed;
        this.directionVectorModifierCallback = directionVectorModifierCallback;
    }

    public DirectionSubAnimPointDefinition(IDirectionSubAnimation subAnimation, double speed) {
        this(subAnimation, speed, (v) -> v);
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
    public void show(AAnimation animation, Location loc, AAnimationTask<?> parentTask) {
        show(animation, loc, new Vector(0, 1, 0), parentTask);
    }

    @Override
    public void show(AAnimation animation, Location loc, Vector v, AAnimationTask<?> parentTask) {
        IDirectionSubAnimation newSubAnimation = (IDirectionSubAnimation) subAnimation.clone();
        newSubAnimation.setPosition(APosition.fromLocation(loc));
        newSubAnimation.setDirection(AnimationDirection.fromMoveVector(
                this.directionVectorModifierCallback.run(v.clone().normalize())
                        .normalize()
                        .multiply(speed)
        ));
        newSubAnimation.show().setParentTask(parentTask);
    }

    @Override
    public DirectionSubAnimPointDefinition clone() {
        DirectionSubAnimPointDefinition obj = (DirectionSubAnimPointDefinition) super.clone();
        obj.subAnimation = (IDirectionSubAnimation) subAnimation.clone();
        return obj;
    }
}
