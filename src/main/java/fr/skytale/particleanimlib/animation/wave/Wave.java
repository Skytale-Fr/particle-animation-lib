package fr.skytale.particleanimlib.animation.wave;


import fr.skytale.particleanimlib.animation.parent.ARoundAnimation;

public class Wave extends ARoundAnimation {
    private double maxRadius;
    private double step;
    private ARoundAnimation anim;

    public Wave() {
    }

    @Override
    public void show() {
        new WaveTask(this);
    }

    /******** Getters & Setters ********/
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

    public ARoundAnimation getCircleAnim() {
        return anim;
    }

    public void setCircleAnim(ARoundAnimation anim) {
        this.anim = anim;
    }
}
