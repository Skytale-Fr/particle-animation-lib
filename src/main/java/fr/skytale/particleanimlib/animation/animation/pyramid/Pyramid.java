package fr.skytale.particleanimlib.animation.animation.pyramid;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.util.Vector;

public class Pyramid extends AAnimation {
    //Vector between the base's center and the pyramid's apex
    private IVariable<Vector> fromCenterToApex;
    //Distance between the center of the base and its vertices
    private IVariable<Double> distanceFromBaseCenterToAnyBaseVertex;
    //Number of vertices of the base
    private IVariable<Integer> nbBaseApex;
    private IVariable<Double> distanceBetweenParticles;

    @Override
    public PyramidTask show() {
        return new PyramidTask(this);
    }

    @Override
    public Pyramid clone() {
        Pyramid obj = (Pyramid) super.clone();
        obj.fromCenterToApex = fromCenterToApex.copy();
        obj.distanceFromBaseCenterToAnyBaseVertex = distanceFromBaseCenterToAnyBaseVertex.copy();
        obj.nbBaseApex = nbBaseApex.copy();
        obj.distanceBetweenParticles = distanceBetweenParticles.copy();
        return obj;
    }

    public IVariable<Vector> getFromCenterToApex() {
        return fromCenterToApex;
    }

    /***********GETTERS & SETTERS***********/

    public void setFromCenterToApex(Vector fromCenterToApex) {
        setFromCenterToApex(new Constant<>(fromCenterToApex));
    }

    public void setFromCenterToApex(IVariable<Vector> fromCenterToApex) {
        this.fromCenterToApex = fromCenterToApex;
    }

    public IVariable<Double> getDistanceFromBaseCenterToAnyBaseVertex() {
        return distanceFromBaseCenterToAnyBaseVertex;
    }

    public void setDistanceFromCenterToAnyBaseVertex(Double distanceFromCenterToAnyBaseVertex) {

    }

    public void setDistanceFromCenterToAnyBaseVertex(IVariable<Double> distanceFromCenterToAnyBaseVertex) {
        this.distanceFromBaseCenterToAnyBaseVertex = distanceFromCenterToAnyBaseVertex;
    }

    public IVariable<Integer> getNbBaseApex() {
        return nbBaseApex;
    }

    public void setNbBaseApex(Integer nbBaseApex) {
        setNbBaseApex(new Constant<>(nbBaseApex));
    }

    public void setNbBaseApex(IVariable<Integer> nbBaseApex) {
        this.nbBaseApex = nbBaseApex;
    }

    public IVariable<Double> getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public void setDistanceBetweenParticles(Double distanceBetweenParticles) {
        setDistanceBetweenParticles(new Constant<>(distanceBetweenParticles));
    }

    public void setDistanceBetweenParticles(IVariable<Double> distanceBetweenParticles) {
        this.distanceBetweenParticles = distanceBetweenParticles;
    }
}
