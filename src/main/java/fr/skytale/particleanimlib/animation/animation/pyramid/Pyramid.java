package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import org.bukkit.util.Vector;

public class Pyramid extends AAnimation implements ISubAnimation {
    //Vector between the base's center and the pyramid's apex
    private IVariable<Vector> fromCenterToApex;
    //Distance between the center of the base and its vertices
    private IVariable<Double> distanceToAnyBaseApex;
    //Number of vertices of the base
    private IVariable<Integer> nbBaseApex;
    private IVariable<Double> distanceBetweenParticles;

    @Override
    public void show() {
        new PyramidTask(this);
    }

    public IVariable<Vector> getFromCenterToApex() {
        return fromCenterToApex;
    }

    /***********GETTERS & SETTERS***********/

    public void setFromCenterToApex(IVariable<Vector> fromCenterToApex) {
        this.fromCenterToApex = fromCenterToApex;
    }

    public IVariable<Double> getDistanceToAnyBaseApex() {
        return distanceToAnyBaseApex;
    }

    public void setDistanceToAnyBaseApex(IVariable<Double> distanceToAnyBaseApex) {
        this.distanceToAnyBaseApex = distanceToAnyBaseApex;
    }

    public IVariable<Integer> getNbBaseApex() {
        return nbBaseApex;
    }

    public void setNbBaseApex(IVariable<Integer> nbBaseApex) {
        this.nbBaseApex = nbBaseApex;
    }

    public IVariable<Double> getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public void setDistanceBetweenParticles(IVariable<Double> distanceBetweenParticles) {
        this.distanceBetweenParticles = distanceBetweenParticles;
    }

    @Override
    public Pyramid clone() {
        Pyramid obj = (Pyramid) super.clone();
        obj.fromCenterToApex = fromCenterToApex.copy();
        obj.distanceToAnyBaseApex = distanceToAnyBaseApex.copy();
        obj.nbBaseApex = nbBaseApex.copy();
        obj.distanceBetweenParticles = distanceBetweenParticles.copy();
        return obj;
    }
}
