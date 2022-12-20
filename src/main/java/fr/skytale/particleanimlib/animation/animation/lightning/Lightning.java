package fr.skytale.particleanimlib.animation.animation.lightning;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.Location;

public class Lightning extends AAnimation {

    private IVariable<Double> distanceBetweenPoints;
    private Location targetLocation;
    private double dispersionAngle;
    private double minDistanceBetweenLightningAngles;
    private double maxDistanceBetweenLightningAngles;
    private boolean convergeToTarget;

    public Lightning() {
    }

    @Override
    public LightningTask show() {
        return new LightningTask(this);
    }

    @Override
    public Lightning clone() {
        Lightning obj = (Lightning) super.clone();
        obj.targetLocation = targetLocation.clone();
        obj.distanceBetweenPoints = distanceBetweenPoints.copy();
        return obj;
    }

    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    /***********GETTERS & SETTERS***********/

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

    public double getMinDistanceBetweenLightningAngles() {
        return minDistanceBetweenLightningAngles;
    }

    public void setMinDistanceBetweenLightningAngles(double minDistanceBetweenLightningAngles) {
        this.minDistanceBetweenLightningAngles = minDistanceBetweenLightningAngles;
    }

    public double getMaxDistanceBetweenLightningAngles() {
        return maxDistanceBetweenLightningAngles;
    }

    public void setMaxDistanceBetweenLightningAngles(double maxDistanceBetweenLightningAngles) {
        this.maxDistanceBetweenLightningAngles = maxDistanceBetweenLightningAngles;
    }

    public boolean isConvergeToTarget() {
        return convergeToTarget;
    }

    public void setConvergeToTarget(boolean convergeToTarget) {
        this.convergeToTarget = convergeToTarget;
    }
}
