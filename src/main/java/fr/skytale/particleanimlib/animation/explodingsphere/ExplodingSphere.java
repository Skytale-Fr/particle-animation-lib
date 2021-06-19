package fr.skytale.particleanimlib.animation.explodingsphere;

import fr.skytale.particleanimlib.parent.ARoundAnimation;

public class ExplodingSphere extends ARoundAnimation {
    private double growthSpeed;
    private int nbCircles;
    private double explosionLimit;

    @Override
    public void show() {
        new ExplodingSphereTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(double growthSpeed) {
        this.growthSpeed = growthSpeed;
    }

    public int getNbCircles() {
        return nbCircles;
    }

    public void setNbCircles(int nbCircles) {
        this.nbCircles = nbCircles;
    }

    public double getExplosionLimit() {
        return explosionLimit;
    }

    public void setExplosionLimit(double explosionLimit) {
        this.explosionLimit = explosionLimit;
    }
}
