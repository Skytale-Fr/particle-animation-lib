package fr.skytale.particleanimlib.animation.animation.lighting;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;

public class Lightning extends AAnimation implements IDirectionSubAnimation {

    private IVariable<Double> distanceBetweenPoints;
    private AnimationDirection direction;
    private double dispersionAngle;
    private double minDistanceBetweenLightingAngles;
    private double maxDistanceBetweenLightingAngles;
    private double maxDistance;
    private boolean convergeToTarget;

    public Lightning() {
    }

    @Override
    public LightningTask show() {
        return new LightningTask(this);
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
    public APointDefinition getPointDefinition() {
        return this.pointDefinition;
    }

    @Override
    public void setPointDefinition(APointDefinition pointDefinition) {
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
