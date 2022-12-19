package fr.skytale.particleanimlib.animation.animation.cuboid;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.util.Vector;

public class Cuboid extends AAnimation implements ISubAnimation {

    private IVariable<Vector> fromLocationToFirstCorner;
    private IVariable<Vector> fromLocationToSecondCorner;
    private IVariable<Double> distanceBetweenPoints;

    public Cuboid() {
    }

    @Override
    public CuboidTask show() {
        return new CuboidTask(this);
    }

    @Override
    public Cuboid clone() {
        Cuboid obj = (Cuboid) super.clone();
        obj.fromLocationToFirstCorner = fromLocationToFirstCorner.copy();
        obj.fromLocationToSecondCorner = fromLocationToSecondCorner.copy();
        obj.distanceBetweenPoints = distanceBetweenPoints.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Vector> getFromLocationToFirstCorner() {
        return fromLocationToFirstCorner;
    }

    public void setFromLocationToFirstCorner(IVariable<Vector> fromLocationToFirstCorner) {
        this.fromLocationToFirstCorner = fromLocationToFirstCorner;
    }

    public IVariable<Vector> getFromLocationToSecondCorner() {
        return fromLocationToSecondCorner;
    }

    public void setFromLocationToSecondCorner(IVariable<Vector> fromLocationToSecondCorner) {
        this.fromLocationToSecondCorner = fromLocationToSecondCorner;
    }

    public IVariable<Double> getDistanceBetweenPoints() {
        return distanceBetweenPoints;
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        this.distanceBetweenPoints = distanceBetweenPoints;
    }
}
