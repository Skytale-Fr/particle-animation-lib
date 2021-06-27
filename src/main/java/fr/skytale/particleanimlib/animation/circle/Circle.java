package fr.skytale.particleanimlib.animation.circle;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.util.Vector;

public class Circle extends ARoundAnimation {
    private Vector u;
    private Vector v;

    //Attributs propre au cercle en rotation
    private Vector axis;
    private double stepAngleAlpha;

    //Attributs pour agrandissement de rayon
    private double stepRadius;

    public Circle() {
    }

    @Override
    public void show() {
        new CircleTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public void setU(Vector u) {
        this.u = u;
    }

    public void setV(Vector v) {
        this.v = v;
    }

    public Vector getV() {
        return v;
    }

    public Vector getU() {
        return u;
    }

    public Vector getAxis() {
        return axis;
    }

    public void setAxis(Vector axis) {
        this.axis = axis;
    }

    public double getStepAngleAlpha() {
        return stepAngleAlpha;
    }

    public void setStepAngleAlpha(double stepAngleAlpha) {
        this.stepAngleAlpha = stepAngleAlpha;
    }

    public double getStepRadius() {
        return stepRadius;
    }

    public void setStepRadius(double stepRadius) {
        this.stepRadius = stepRadius;
    }

    @Override
    public Object clone() {
        Circle obj = (Circle) super.clone();
        obj.u = u.clone();
        obj.v = v.clone();
        obj.axis = this.getAxis() == null ? null : this.getAxis().clone();
        return obj;
    }
}
