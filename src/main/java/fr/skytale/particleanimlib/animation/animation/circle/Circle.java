package fr.skytale.particleanimlib.animation.animation.circle;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;

public class Circle extends ARoundAnimation {
    private IVariable<Double> angleBetweenEachPoint;

    public Circle() {
    }

    @Override
    public CircleTask show() {
        return new CircleTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getAngleBetweenEachPoint() {
        return angleBetweenEachPoint;
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        this.angleBetweenEachPoint = angleBetweenEachPoint;
    }

    @Override
    public Circle clone() {
        Circle obj = (Circle) super.clone();
        obj.nbPoints = nbPoints.copy();
        return obj;
    }
}
