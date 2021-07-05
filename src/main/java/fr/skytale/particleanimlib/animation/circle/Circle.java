package fr.skytale.particleanimlib.animation.circle;


import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingRoundAnimation;
import org.bukkit.util.Vector;

public class Circle extends ARotatingRoundAnimation {
    private IVariable<Vector> u;
    private IVariable<Vector> v;
    private IVariable<Integer> nbPoints;

    public Circle() {
    }

    @Override
    public void show() {
        new CircleTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Vector> getU() {
        return u;
    }

    public void setU(IVariable<Vector> u) {
        this.u = u;
    }

    public IVariable<Vector> getV() {
        return v;
    }

    public void setV(IVariable<Vector> v) {
        this.v = v;
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }

    @Override
    public Circle clone() {
        Circle obj = (Circle) super.clone();
        obj.u = u.copy();
        obj.v = v.copy();
        return obj;
    }
}
