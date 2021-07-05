package fr.skytale.particleanimlib.animation.wave;


import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.util.Vector;

public class Wave extends AAnimation {
    private Vector u;
    private Vector v;
    protected IVariable<Double> angleBetweenEachPoint;
    private IVariable<Integer> nbPoints;
    private double radiusMax;
    private IVariable<Double> radiusStep;
    protected double radiusStart;

    public Wave() {
    }

    @Override
    public void show() {
        new WaveTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public Vector getU() {
        return u;
    }

    public void setU(Vector u) {
        this.u = u;
    }

    public Vector getV() {
        return v;
    }

    public void setV(Vector v) {
        this.v = v;
    }

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

    @Override
    public Wave clone() {
        Wave obj = (Wave) super.clone();
        obj.u = u.clone();
        obj.v = v.clone();
        obj.angleBetweenEachPoint = angleBetweenEachPoint.copy();
        obj.nbPoints = nbPoints.copy();
        obj.radiusStep = radiusStep.copy();
        return obj;
    }
}
