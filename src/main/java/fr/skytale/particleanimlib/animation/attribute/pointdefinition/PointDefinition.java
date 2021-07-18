package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class PointDefinition implements Cloneable {

    protected ShowMethodParameters showMethodParameters;
    protected boolean hasSubAnimation;

    protected PointDefinition(ShowMethodParameters showMethodParameters, boolean hasSubAnimation) {
        this.showMethodParameters = showMethodParameters;
        this.hasSubAnimation = hasSubAnimation;
    }

    public static ParticlePointDefinition fromParticleTemplate(ParticleTemplate particleTemplate) {
        return new ParticlePointDefinition(particleTemplate);
    }

    public static PlaneSubAnimPointDefinition fromSubAnim(IPlaneSubAnimation planeAnimation) {
        return new PlaneSubAnimPointDefinition(planeAnimation);
    }

    public static LocationSubAnimPointDefinition fromSubAnim(ISubAnimation locationAnimation) {
        return new LocationSubAnimPointDefinition(locationAnimation);
    }

    public static PlaneSubAnimPointDefinition fromSubAnim(IPlaneSubAnimation planeAnimation, boolean computePlane) {
        return new PlaneSubAnimPointDefinition(planeAnimation, computePlane);
    }

    public static DirectionSubAnimPointDefinition fromSubAnim(IDirectionSubAnimation directionAnimation, double speed) {
        return new DirectionSubAnimPointDefinition(directionAnimation, speed);
    }

    public static DirectionSubAnimPointDefinition fromSubAnim(IDirectionSubAnimation directionAnimation, double speed, Vector direction, boolean isDirectionRelative) {
        return new DirectionSubAnimPointDefinition(directionAnimation, speed, direction, isDirectionRelative);
    }

    public ShowMethodParameters getShowMethodParameters() {
        return showMethodParameters;
    }

    public boolean hasSubAnimation() {
        return hasSubAnimation;
    }

    public abstract void show(Location loc);

    public abstract void show(Location loc, Vector fromCenterToPoint);

    @Override
    public PointDefinition clone() {
        PointDefinition obj = null;
        try {
            obj = (PointDefinition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        return obj;
    }

    public enum ShowMethodParameters {LOCATION, LOCATION_AND_DIRECTION}
}
