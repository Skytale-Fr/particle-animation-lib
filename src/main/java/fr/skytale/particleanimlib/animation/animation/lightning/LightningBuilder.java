package fr.skytale.particleanimlib.animation.animation.lightning;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.Location;

public class LightningBuilder extends AAnimationBuilder<Lightning, LightningTask> {

    public LightningBuilder() {
        super();
        animation.setDistanceBetweenPoints(new Constant<>(0.5));
        animation.setMaxDistanceBetweenLightningAngles(30.0);
        animation.setMinDistanceBetweenLightningAngles(1.0);
        animation.setDispersionAngle(Math.PI / 6);
        animation.setConvergeToTarget(true);
    }

    @Override
    protected Lightning initAnimation() {
        return new Lightning();
    }

    @Override
    public Lightning getAnimation() {
        checkNotNull(animation.getTargetLocation(), "targetLocation should not be null");
        checkNotNull(animation.getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        if (animation.getDispersionAngle() < 0)
            throw new IllegalArgumentException("dispersionAngle should be positive");
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        checkSuperior(animation.getDistanceBetweenPoints(), new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        if (animation.getMinDistanceBetweenLightningAngles() < 0)
            throw new IllegalArgumentException("minDistanceBetweenLightningAngles should be positive");
        if (animation.getMaxDistanceBetweenLightningAngles() < 0)
            throw new IllegalArgumentException("maxDistanceBetweenLightningAngles should be positive");
        if (animation.getMinDistanceBetweenLightningAngles() > animation.getMaxDistanceBetweenLightningAngles())
            throw new IllegalArgumentException("maxDistanceBetweenLightningAngles should be greater than minDistanceBetweenLightningAngles");
        return super.getAnimation();
    }

    /**
     * Set where the lightning will hit the ground
     * @param location the target location of the lightning
     */
    public void setTargetLocation(Location location) {
        checkNotNull(location, "targetLocation should not be null");
        animation.setTargetLocation(location);
    }

    /**
     * Defines how far the lightning bolt can deviate from a vertical trajectory towards its point of impact.
     * @param dispersionAngle a radian angle defining how far the lightning bolt can deviate from a vertical trajectory towards its point of impact.
     */
    public void setDispersionAngle(double dispersionAngle) {
        if (dispersionAngle < 0) throw new IllegalArgumentException("dispersionAngle should be positive");
        animation.setDispersionAngle(dispersionAngle);
    }

    /**
     * Defines the distance between each point when lightning travels in a straight line
     * @param distanceBetweenPoints the distance between each point of the lightning
     */
    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        checkSuperior(distanceBetweenPoints, new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    /**
     * Defines the distance between each point when lightning travels in a straight line
     * @param distanceBetweenPoints the distance between each point of the lightning
     */
    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }

    /**
     * Defines the minimum distance separating 2 points where the lightning changes trajectory
     * @param minDistanceBetweenLightningAngles the minimum distance separating 2 points where the lightning changes trajectory
     */
    public void setMinDistanceBetweenLightningAngles(double minDistanceBetweenLightningAngles) {
        if (minDistanceBetweenLightningAngles < 0)
            throw new IllegalArgumentException("minDistanceBetweenLightningAngles should be positive");
        animation.setMinDistanceBetweenLightningAngles(minDistanceBetweenLightningAngles);
    }


    /**
     * Defines the maximum distance separating 2 points where the lightning changes trajectory
     * @param maxDistanceBetweenLightningAngles the maximum distance separating 2 points where the lightning changes trajectory
     */
    public void setMaxDistanceBetweenLightningAngles(double maxDistanceBetweenLightningAngles) {
        if (maxDistanceBetweenLightningAngles < 0)
            throw new IllegalArgumentException("maxDistanceBetweenLightningAngles should be positive");
        animation.setMaxDistanceBetweenLightningAngles(maxDistanceBetweenLightningAngles);
    }

    /**
     * Defines if the lightning should converge to the target location
     * @param convergeToTarget true if the lightning should converge to the target location
     */
    public void setConvergeToTarget(boolean convergeToTarget) {
        animation.setConvergeToTarget(convergeToTarget);
    }
}
