package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

public abstract class ARoundAnimation extends AAnimation {
    protected IVariable<Double> radius;
    protected IVariable<Integer> nbPoints;

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    @Override
    public ARoundAnimation clone() {
        ARoundAnimation obj = null;
        obj = (ARoundAnimation) super.clone();
        obj.radius = radius.copy();
        obj.nbPoints = nbPoints.copy();
        return obj;
    }

}
