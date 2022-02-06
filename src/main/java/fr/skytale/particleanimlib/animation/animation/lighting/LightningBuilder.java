package fr.skytale.particleanimlib.animation.animation.lighting;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

import java.awt.*;

public class LightningBuilder extends AAnimationBuilder<Lightning, LightningTask> {

    public LightningBuilder() {
        super();
        animation.setDistanceBetweenPoints(new Constant<>(0.5));
        animation.setMaxDistanceBetweenLightingAngles(30.0);
        animation.setMinDistanceBetweenLightingAngles(1.0);
        animation.setMaxDistance(200);
        animation.setPointDefinition(APointDefinition.fromParticleTemplate(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null)));
        animation.setDispersionAngle(Math.PI / 6);
        animation.setConvergeToTarget(true);
    }


    /*********SETTERS des éléments spécifiques a la spirale ***********/

    public void setDirection(AnimationDirection direction) {
        checkNotNull(direction, "direction should not be null");
        animation.setDirection(direction);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
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

    public void setMinDistanceBetweenLightingAngles(double minDistanceBetweenLightingAngles) {
        if (minDistanceBetweenLightingAngles < 0)
            throw new IllegalArgumentException("minDistanceBetweenLightingAngles should be positive");
        animation.setMinDistanceBetweenLightingAngles(minDistanceBetweenLightingAngles);
    }

    public void setMaxDistanceBetweenLightingAngles(double maxDistanceBetweenLightingAngles) {
        if (maxDistanceBetweenLightingAngles < 0)
            throw new IllegalArgumentException("maxDistanceBetweenLightingAngles should be positive");
        animation.setMaxDistanceBetweenLightingAngles(maxDistanceBetweenLightingAngles);
    }

    public void setMaxDistance(double maxDistance) {
        if (maxDistance < 0) throw new IllegalArgumentException("maxDistance should be positive");
        animation.setMaxDistance(maxDistance);
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
        checkNotNull(animation.getDirection(), "direction should not be null");
        checkNotNull(animation.getPointDefinition(), POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        if (animation.getDispersionAngle() < 0)
            throw new IllegalArgumentException("dispersionAngle should be positive");
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        checkSuperior(animation.getDistanceBetweenPoints(), new Constant<>(0.1), "distanceBetweenPoints should be greater than 0.1", false);
        if (animation.getMinDistanceBetweenLightingAngles() < 0)
            throw new IllegalArgumentException("minDistanceBetweenLightingAngles should be positive");
        if (animation.getMaxDistanceBetweenLightingAngles() < 0)
            throw new IllegalArgumentException("maxDistanceBetweenLightingAngles should be positive");
        if (animation.getMinDistanceBetweenLightingAngles() > animation.getMaxDistanceBetweenLightingAngles())
            throw new IllegalArgumentException("maxDistanceBetweenLightingAngles should be greater than minDistanceBetweenLightingAngles");
        if (animation.getMaxDistance() < 0) throw new IllegalArgumentException("maxDistance should be positive");
        return super.getAnimation();
    }
}
