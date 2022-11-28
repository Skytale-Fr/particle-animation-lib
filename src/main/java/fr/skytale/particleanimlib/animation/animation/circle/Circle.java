package fr.skytale.particleanimlib.animation.animation.circle;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;

public class Circle extends ARoundAnimation implements ISubAnimation {
    private IVariable<Integer> nbPoints;

    public Circle() {
    }

    @Override
    public CircleTask show() {
        return new CircleTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    @Override
    public Circle clone() {
        Circle obj = (Circle) super.clone();
        obj.nbPoints = nbPoints.copy();
        return obj;
    }
}
