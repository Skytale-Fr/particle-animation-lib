package fr.skytale.particleanimlib.parent;

import org.bukkit.util.Vector;

public abstract class ARotatingAnimation extends AAnimation {

    //If rotation is required
    private Vector axis;
    private double stepAngleAlpha;
    //If rotation changes
    private Integer axisChangeFrequency = null;
    private double stepAngleAlphaChangeFactor;
    private double stepAngleAlphaMax;
    private Integer stepAngleAlphaChangeFrequency = null;


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

    public Integer getAxisChangeFrequency() {
        return axisChangeFrequency;
    }

    public void setAxisChangeFrequency(Integer axisChangeFrequency) {
        this.axisChangeFrequency = axisChangeFrequency;
    }

    public double getStepAngleAlphaChangeFactor() {
        return stepAngleAlphaChangeFactor;
    }

    public void setStepAngleAlphaChangeFactor(double stepAngleAlphaChangeFactor) {
        this.stepAngleAlphaChangeFactor = stepAngleAlphaChangeFactor;
    }

    public double getStepAngleAlphaMax() {
        return stepAngleAlphaMax;
    }

    public void setStepAngleAlphaMax(double stepAngleAlphaMax) {
        this.stepAngleAlphaMax = stepAngleAlphaMax;
    }

    public Integer getStepAngleAlphaChangeFrequency() {
        return stepAngleAlphaChangeFrequency;
    }

    public void setStepAngleAlphaChangeFrequency(Integer stepAngleAlphaChangeFrequency) {
        this.stepAngleAlphaChangeFrequency = stepAngleAlphaChangeFrequency;
    }


    @Override
    public Object clone() {
        ARotatingAnimation obj = null;
        obj = (ARotatingAnimation) super.clone();
        obj.axis = axis == null ? null : axis.clone();
        return obj;
    }
}
