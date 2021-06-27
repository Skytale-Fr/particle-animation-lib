package fr.skytale.particleanimlib.animation.cuboid;

import fr.skytale.particleanimlib.parent.ARotatingAnimation;
import org.bukkit.util.Vector;

public class Cuboid extends ARotatingAnimation {

    private Vector fromLocationToFirstCorner;
    private Vector fromLocationToSecondCorner;
    private double step;

    public Cuboid() {
    }

    @Override
    public void show() {
        new CuboidTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public Vector getFromLocationToFirstCorner() {
        return fromLocationToFirstCorner;
    }

    public void setFromLocationToFirstCorner(Vector fromLocationToFirstCorner) {
        this.fromLocationToFirstCorner = fromLocationToFirstCorner;
    }

    public Vector getFromLocationToSecondCorner() {
        return fromLocationToSecondCorner;
    }

    public void setFromLocationToSecondCorner(Vector fromLocationToSecondCorner) {
        this.fromLocationToSecondCorner = fromLocationToSecondCorner;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }


    @Override
    public Object clone() {
        Cuboid obj = (Cuboid) super.clone();
        obj.fromLocationToFirstCorner = fromLocationToFirstCorner.clone();
        obj.fromLocationToSecondCorner = fromLocationToSecondCorner.clone();
        return obj;
    }
}
