package fr.skytale.particleanimlib.animation.animation.wave;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.util.Vector;

public class Wave extends AAnimation implements ISubAnimation {
    protected IVariable<Double> angleBetweenEachPoint;
    protected double radiusStart;
    private IVariable<Integer> nbPoints;
    private double radiusMax;
    private IVariable<Double> radiusStep;
    private boolean positiveHeight;

    public Wave() {
    }

    @Override
    public WaveTask show() {
        return new WaveTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    public double getRadiusMax() {
        return radiusMax;
    }

    public void setRadiusMax(double radiusMax) {
        this.radiusMax = radiusMax;
    }

    public double getRadiusStart() {
        return radiusStart;
    }

    public void setRadiusStart(double radiusStart) {
        this.radiusStart = radiusStart;
    }

    public IVariable<Double> getRadiusStep() {
        return radiusStep;
    }

    public void setRadiusStep(IVariable<Double> radiusStep) {
        this.radiusStep = radiusStep;
    }

    public IVariable<Double> getAngleBetweenEachPoint() {
        return angleBetweenEachPoint;
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        this.angleBetweenEachPoint = angleBetweenEachPoint;
    }

    public boolean getPositiveHeight() {
        return positiveHeight;
    }

    public void setPositiveHeight(boolean positiveHeight) {
        this.positiveHeight = positiveHeight;
    }

    @Override
    public Wave clone() {
        Wave obj = (Wave) super.clone();
        obj.angleBetweenEachPoint = angleBetweenEachPoint.copy();
        obj.nbPoints = nbPoints.copy();
        obj.radiusStep = radiusStep.copy();
        return obj;
    }
}
