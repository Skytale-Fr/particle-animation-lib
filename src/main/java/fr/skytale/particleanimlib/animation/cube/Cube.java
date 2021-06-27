package fr.skytale.particleanimlib.animation.cube;

import fr.skytale.particleanimlib.parent.AAnimation;

public class Cube extends AAnimation {
    private double sideLength;
    private double step;

    public Cube() {
    }

    @Override
    public void show() {
        new CubeTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public Object clone() {
        Cube obj = (Cube) super.clone();
        return obj;
    }
}
