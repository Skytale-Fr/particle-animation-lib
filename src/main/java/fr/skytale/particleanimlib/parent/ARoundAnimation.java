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
        if(radius<=0)
            radius = 1.0;
        this.radius = radius;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        if(nbPoints<=0)
            nbPoints = (int)radius * 20;
        this.nbPoints = nbPoints;
        setStepAngle(2 * Math.PI / nbPoints);
    }

    public double getStepAngle() {
        return stepAngle;
    }

    public void setStepAngle(double stepAngle) {
        this.stepAngle = stepAngle;
    }
}
