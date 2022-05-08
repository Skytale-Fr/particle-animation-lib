package fr.skytale.particleanimlib.animation.animation.polygon;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import org.bukkit.util.Vector;

public class Polygon extends ARotatingAnimation implements IPlaneSubAnimation {
    private Vector u;
    private Vector v;
    private IVariable<Integer> nbVertices;
    private IVariable<Double> distanceFromCenterToVertices;
    private IVariable<Double> distanceBetweenPoints;

    public Polygon() {
    }

    @Override
    public PolygonTask show() {
        return new PolygonTask(this);
    }

    /***********GETTERS & SETTERS***********/

    @Override
    public Vector getU() {
        return u;
    }

    @Override
    public void setU(Vector u) {
        this.u = u;
    }

    @Override
    public Vector getV() {
        return v;
    }

    @Override
    public void setV(Vector v) {
        this.v = v;
    }

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

    @Override
    public Polygon clone() {
        Polygon obj = (Polygon) super.clone();
        obj.u = u.clone();
        obj.v = v.clone();
        obj.nbVertices = nbVertices.copy();
        obj.distanceBetweenPoints = distanceBetweenPoints.copy();
        obj.distanceFromCenterToVertices = distanceFromCenterToVertices.copy();
        return obj;
    }
}
