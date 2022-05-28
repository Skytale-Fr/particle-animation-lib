package fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.*;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
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

    public static CallbackPointDefinition fromCallback(PointShowCallback pointShowCallback) {
        return new CallbackPointDefinition(pointShowCallback);
    }

    public ShowMethodParameters getShowMethodParameters() {
        return showMethodParameters;
    }

    public boolean hasSubAnimation() {
        return hasSubAnimation;
    }

    /**
     * Creates a clone and shows the sub animation
     * @param animation The animation to show
     * @param loc The location to show the animation
     * @param parentTask The animation task that calls this method
     */
    public abstract void show(AAnimation animation, Location loc, AAnimationTask<?> parentTask);

    /**
     * Creates a clone and shows the sub animation
     * @param animation The animation to show
     * @param loc The location to show the animation
     * @param fromCenterToPoint Can be used to set an animation's direction
     * @param parentTask The animation task that calls this method
     */
    public abstract void show(AAnimation animation, Location loc, Vector fromCenterToPoint, AAnimationTask<?> parentTask);

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
