package fr.skytale.particleanimlib.cube;

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
}
