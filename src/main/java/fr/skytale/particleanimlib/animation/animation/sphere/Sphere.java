package fr.skytale.particleanimlib.animation.animation.sphere;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;


public class Sphere extends ARoundAnimation {

    public enum PropagationType {
        TOP_TO_BOTTOM, BOTTOM_TO_TOP;
    }

    public enum Type {
        FULL, HALF_TOP, HALF_BOTTOM;
    }

    private IVariable<Integer> nbCircles;
    private PropagationType propagationType;
    private IVariable<Integer> simultaneousCircles;
    private Type type;

    public Sphere() {
    }

    @Override
    public void show() {
        new SphereTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Integer> getNbCircles() {
        return nbCircles;
    }

    public void setNbCircles(IVariable<Integer> nbCircles) {
        this.nbCircles = nbCircles;
    }

    public IVariable<Integer> getSimultaneousCircles() {
        return simultaneousCircles;
    }

    public void setSimultaneousCircles(IVariable<Integer> simultaneousCircles) {
        this.simultaneousCircles = simultaneousCircles;
    }

    public PropagationType getPropagationType() {
        return propagationType;
    }

    public void setPropagationType(PropagationType propagationType) {
        this.propagationType = propagationType;
    }

    public Type getSphereType() {
        return type;
    }

    public void setSphereType(Type type) {
        this.type = type;
    }

    @Override
    public Sphere clone() {
        Sphere obj = (Sphere) super.clone();
        obj.nbCircles = nbCircles.copy();
        obj.simultaneousCircles = simultaneousCircles.copy();
        return obj;
    }
}
