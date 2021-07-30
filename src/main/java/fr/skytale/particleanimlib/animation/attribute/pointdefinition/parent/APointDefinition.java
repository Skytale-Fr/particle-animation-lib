package fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.DirectionSubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.LocationSubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PlaneSubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class APointDefinition implements Cloneable {

    protected ShowMethodParameters showMethodParameters;
    protected boolean hasSubAnimation;

    protected APointDefinition(ShowMethodParameters showMethodParameters, boolean hasSubAnimation) {
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

    public static DirectionSubAnimPointDefinition fromSubAnim(IDirectionSubAnimation directionAnimation, double speed, DirectionVectorModifierCallback vectorModifierCallback) {
        return new DirectionSubAnimPointDefinition(directionAnimation, speed, vectorModifierCallback);
    }

    public ShowMethodParameters getShowMethodParameters() {
        return showMethodParameters;
    }

    public boolean hasSubAnimation() {
        return hasSubAnimation;
    }

    public abstract void show(AAnimation animation, Location loc);

    public abstract void show(AAnimation animation, Location loc, Vector fromCenterToPoint);

    @Override
    public APointDefinition clone() {
        APointDefinition obj = null;
        try {
            obj = (APointDefinition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        return obj;
    }

    public enum ShowMethodParameters {LOCATION, LOCATION_AND_DIRECTION}
}
