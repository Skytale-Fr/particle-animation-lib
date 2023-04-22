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


    public void setTargetLocation(Location location) {
        checkNotNull(location, "targetLocation should not be null");
        animation.setTargetLocation(location);
    }

    public void setDispersionAngle(double dispersionAngle) {
        if (dispersionAngle < 0) throw new IllegalArgumentException("dispersionAngle should be positive");
        animation.setDispersionAngle(dispersionAngle);
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        checkSuperior(distanceBetweenPoints, new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }

    public void setMinDistanceBetweenLightningAngles(double minDistanceBetweenLightningAngles) {
        if (minDistanceBetweenLightningAngles < 0)
            throw new IllegalArgumentException("minDistanceBetweenLightningAngles should be positive");
        animation.setMinDistanceBetweenLightningAngles(minDistanceBetweenLightningAngles);
    }

    public void setMaxDistanceBetweenLightningAngles(double maxDistanceBetweenLightningAngles) {
        if (maxDistanceBetweenLightningAngles < 0)
            throw new IllegalArgumentException("maxDistanceBetweenLightningAngles should be positive");
        animation.setMaxDistanceBetweenLightningAngles(maxDistanceBetweenLightningAngles);
    }

    public void setConvergeToTarget(boolean convergeToTarget) {
        animation.setConvergeToTarget(convergeToTarget);
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
}
