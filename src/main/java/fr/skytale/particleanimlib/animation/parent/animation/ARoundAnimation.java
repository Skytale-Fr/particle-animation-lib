package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

public abstract class ARoundAnimation extends AAnimation {
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
    public ARoundAnimation clone() {
        ARoundAnimation obj = null;
        obj = (ARoundAnimation) super.clone();
        obj.radius = radius.copy();
        obj.angleBetweenEachPoint = angleBetweenEachPoint.copy();
        return obj;
    }
}
