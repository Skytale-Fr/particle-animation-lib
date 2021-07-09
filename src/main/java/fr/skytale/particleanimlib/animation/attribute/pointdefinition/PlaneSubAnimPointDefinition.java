package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PlaneSubAnimPointDefinition extends SubAnimPointDefinition {

    protected IPlaneSubAnimation subAnimation;

    public PlaneSubAnimPointDefinition(IPlaneSubAnimation subAnimation) {
        this(subAnimation, true);
    }

    public PlaneSubAnimPointDefinition(IPlaneSubAnimation subAnimation, boolean computePlane) {
        super(computePlane ? ShowMethodParameters.LOCATION_AND_DIRECTION : ShowMethodParameters.LOCATION);
        this.subAnimation = subAnimation;
    }

    @Override
    public ISubAnimation getSubAnimation() {
        return subAnimation;
    }

    public IPlaneSubAnimation getSubPlaneAnimation() {
        return subAnimation;
    }

    @Override
    public void show(Location loc) {
        IPlaneSubAnimation newSubAnimation = (IPlaneSubAnimation) subAnimation.clone();
        newSubAnimation.setPosition(APosition.fromLocation(loc));
        newSubAnimation.show();
    }

    @Override
    public void show(Location loc, Vector fromCenterToPoint) {
        if (this.showMethodParameters == ShowMethodParameters.LOCATION_AND_DIRECTION) {
            RotatableVector.Plane2D plane = new RotatableVector(fromCenterToPoint).getPlane(loc);
            subAnimation.setPosition(APosition.fromLocation(loc));
            subAnimation.setU(plane.u);
            subAnimation.setV(plane.v);
            subAnimation.show();
        } else {
            show(loc);
        }
    }

    @Override
    public PlaneSubAnimPointDefinition clone() {
        PlaneSubAnimPointDefinition obj = (PlaneSubAnimPointDefinition) super.clone();
        obj.subAnimation = (IPlaneSubAnimation) subAnimation.clone();
        return obj;
    }
}
