package fr.skytale.particleanimlib.spiral;

import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;

public class Spiral extends ARoundAnimation {
    private double step;
    private Location target;
    private  double growthSpeed;

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

    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(double growthSpeed) {
        this.growthSpeed = growthSpeed;
    }
}
