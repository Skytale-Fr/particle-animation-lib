package fr.skytale.particleanimlib.animation.animation.lighting;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;

public class Lightning extends AAnimation implements IDirectionSubAnimation, ISubAnimationContainer {

    private AnimationDirection direction;
    private PointDefinition pointDefinition;
    private double dispersionAngle;
    private IVariable<Double> distanceBetweenPoints;
    private double minDistanceBetweenLightingAngles;
    private double maxDistanceBetweenLightingAngles;
    private double maxDistance;
    private boolean convergeToTarget;

    public Lightning() {
    }

    @Override
    public void show() {
        new LightningTask(this);
    }

    /***********GETTERS & SETTERS***********/

    @Override
    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
    public void setDirection(AnimationDirection direction) {
        this.direction = direction;
    }

    @Override
    public PointDefinition getPointDefinition() {
        return this.pointDefinition;
    }

    @Override
    public void setPointDefinition(PointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
    }

    public double getDispersionAngle() {
        return dispersionAngle;
    }

    public void setDispersionAngle(double dispersionAngle) {
        this.dispersionAngle = dispersionAngle;
    }

    public IVariable<Double> getDistanceBetweenPoints() {
        return distanceBetweenPoints;
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        this.distanceBetweenPoints = distanceBetweenPoints;
    }

    public double getMinDistanceBetweenLightingAngles() {
        return minDistanceBetweenLightingAngles;
    }

    public void setMinDistanceBetweenLightingAngles(double minDistanceBetweenLightingAngles) {
        this.minDistanceBetweenLightingAngles = minDistanceBetweenLightingAngles;
    }

    public double getMaxDistanceBetweenLightingAngles() {
        return maxDistanceBetweenLightingAngles;
    }

    public void setMaxDistanceBetweenLightingAngles(double maxDistanceBetweenLightingAngles) {
        this.maxDistanceBetweenLightingAngles = maxDistanceBetweenLightingAngles;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public ParticleTemplate getMainParticle() {
        if (this.pointDefinition instanceof ParticlePointDefinition) {
            return ((ParticlePointDefinition)pointDefinition).getParticleTemplate();
        }
        throw new IllegalStateException("ParticleTemplate is not defined since this animation PointDefinition defines a sub animation");
    }

    @Override
    public void setMainParticle(ParticleTemplate mainParticle) {
        setPointDefinition(PointDefinition.fromParticleTemplate(mainParticle));
    }

    public boolean isConvergeToTarget() {
        return convergeToTarget;
    }

    public void setConvergeToTarget(boolean convergeToTarget) {
        this.convergeToTarget = convergeToTarget;
    }

    @Override
    public Lightning clone() {
        Lightning obj = (Lightning) super.clone();
        obj.direction = direction.clone();
        obj.pointDefinition = pointDefinition.clone();
        obj.distanceBetweenPoints = distanceBetweenPoints.copy();
        return obj;
    }
}
