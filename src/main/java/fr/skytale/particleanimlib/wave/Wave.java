package fr.skytale.particleanimlib.wave;


import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;

public class Wave extends ARoundAnimation {
    private double maxRadius;
    private double step;
    private AAnimation circleAnim;

    public Wave() {
    }

    @Override
    public void show() {
        new WaveTask(this);
    }

    /***********GETTERS & SETTERS***********/
    public double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public AAnimation getCircleAnim() {
        return circleAnim;
    }

    public void setCircleAnim(AAnimation circleAnim) {
        this.circleAnim = circleAnim;
    }
}
