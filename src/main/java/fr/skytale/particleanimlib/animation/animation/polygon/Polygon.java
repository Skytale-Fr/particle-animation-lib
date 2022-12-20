package fr.skytale.particleanimlib.animation.animation.polygon;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;

public class Polygon extends AAnimation implements ISubAnimation {
    private IVariable<Integer> nbVertices;
    private IVariable<Double> distanceFromCenterToVertices;
    private IVariable<Double> distanceBetweenPoints;

    public Polygon() {
    }

    @Override
    public PolygonTask show() {
        return new PolygonTask(this);
    }

    @Override
    public Polygon clone() {
        Polygon obj = (Polygon) super.clone();
        obj.nbVertices = nbVertices.copy();
        obj.distanceBetweenPoints = distanceBetweenPoints.copy();
        obj.distanceFromCenterToVertices = distanceFromCenterToVertices.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Integer> getNbVertices() {
        return nbVertices;
    }

    public void setNbVertices(IVariable<Integer> nbVertices) {
        this.nbVertices = nbVertices;
    }

    public IVariable<Double> getDistanceBetweenPoints() {
        return distanceBetweenPoints;
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        this.distanceBetweenPoints = distanceBetweenPoints;
    }

    public IVariable<Double> getDistanceFromCenterToVertices() {
        return distanceFromCenterToVertices;
    }

    public void setDistanceFromCenterToVertices(IVariable<Double> distanceFromCenterToVertices) {
        this.distanceFromCenterToVertices = distanceFromCenterToVertices;
    }
}
