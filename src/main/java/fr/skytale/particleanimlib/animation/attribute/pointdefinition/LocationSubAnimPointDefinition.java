package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
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
    public void show(AAnimation animation, Location loc, AAnimationTask<?> parentTask) {
        ISubAnimation newSubAnimation = (ISubAnimation) subAnimation.clone();
        newSubAnimation.setPosition(APosition.fromLocation(loc));
        newSubAnimation.show().setParentTask(parentTask);
    }

    @Override
    public void show(AAnimation animation, Location loc, Vector fromCenterToPoint, AAnimationTask<?> parentTask) {
        throw new IllegalStateException("This method should never be called for DirectionSubAnimPointDefinition. show(Location, Vector, AAnimationTask) should be called instead.");
    }

    @Override
    public LocationSubAnimPointDefinition clone() {
        LocationSubAnimPointDefinition obj = (LocationSubAnimPointDefinition) super.clone();
        obj.subAnimation = (ISubAnimation) subAnimation.clone();
        return obj;
    }
}
