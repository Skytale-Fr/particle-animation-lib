package fr.skytale.particleanimlib.parent;

public abstract class ARoundAnimation extends AAnimation {
    protected double radius;
    protected int nbPoints;
    protected double stepAngle;

    /***********GETTERS & SETTERS***********/

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public double getStepAngle() {
        return stepAngle;
    }

    public void setStepAngle(double stepAngle) {
        this.stepAngle = stepAngle;
    }
}
