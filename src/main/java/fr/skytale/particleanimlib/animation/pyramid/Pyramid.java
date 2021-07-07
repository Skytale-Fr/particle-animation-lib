package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.animation.parent.AAnimation;
import org.bukkit.util.Vector;

public class Pyramid extends AAnimation {
    //Vector between the base's center and the pyramid's apex
    private Vector fromCenterToApex;
    //Distance between the center of the base and its vertices
    private double distanceToAnyBaseApex;
    //Number of vertices of the base
    private int nbBaseApex;
    private double distanceBetweenParticles;

    @Override
    public void show() {
        new PyramidTask(this);
    }

    /***********GETTERS & SETTERS***********/
    public Vector getFromCenterToApex() {
        return fromCenterToApex;
    }

    public void setFromCenterToApex(Vector fromCenterToApex) {
        this.fromCenterToApex = fromCenterToApex;
    }

    public double getDistanceToAnyBaseApex() {
        return distanceToAnyBaseApex;
    }

    public void setDistanceToAnyBaseApex(double distanceToAnyBaseApex) {
        this.distanceToAnyBaseApex = distanceToAnyBaseApex;
    }

    public int getNbBaseApex() {
        return nbBaseApex;
    }

    public void setNbBaseApex(int nbBaseApex) {
        this.nbBaseApex = nbBaseApex;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public void setDistanceBetweenParticles(double distanceBetweenParticles) {
        this.distanceBetweenParticles = distanceBetweenParticles;
    }

    @Override
    public Object clone() {
        Pyramid obj = (Pyramid) super.clone();
        obj.fromCenterToApex = fromCenterToApex.clone();
        return obj;
    }
}
