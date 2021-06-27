package fr.skytale.particleanimlib.animation.spiral;

import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.util.Vector;

public class Spiral extends ARoundAnimation {
    private double step;
    private Vector target;
    private double growthSpeed;

    public Spiral() {
    }

    @Override
    public void show() {
        new SpiralTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Vector getTarget() {
        return target;
    }

    public void setTarget(Vector target) {
        this.target = target;
    }

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(double growthSpeed) {
        this.growthSpeed = growthSpeed;
    }

    @Override
    public Object clone() {
        Spiral obj = (Spiral) super.clone();
        obj.target = target.clone();
        return obj;
    }
}
