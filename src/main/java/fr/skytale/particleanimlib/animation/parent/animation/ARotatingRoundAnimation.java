package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

public abstract class ARotatingRoundAnimation extends ARotatingAnimation {
    protected IVariable<Double> radius;
    protected IVariable<Double> angleBetweenEachPoint;

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Double> getAngleBetweenEachPoint() {
        return angleBetweenEachPoint;
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        this.angleBetweenEachPoint = angleBetweenEachPoint;
    }

    @Override
    public ARotatingRoundAnimation clone() {
        ARotatingRoundAnimation obj = null;
        obj = (ARotatingRoundAnimation) super.clone();
        obj.radius = radius.copy();
        obj.angleBetweenEachPoint = angleBetweenEachPoint.copy();
        return obj;
    }
}
