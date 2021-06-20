package fr.skytale.particleanimlib.animation.sphere;


import fr.skytale.particleanimlib.attributes.PropagationType;
import fr.skytale.particleanimlib.attributes.SphereType;
import fr.skytale.particleanimlib.parent.ARoundAnimation;

public class Sphere extends ARoundAnimation {
    private int nbCircles;
    private PropagationType propagationType;
    private int simultaneousCircles;
    private SphereType sphereType;

    public Sphere() {
    }

    @Override
    public void show() {
        new SphereTask(this);
    }

    /***********GETTERS & SETTERS***********/
    public int getNbCircles() {
        return nbCircles;
    }

    public void setNbCircles(int nbCircles) {
        this.nbCircles = nbCircles;
    }

    public PropagationType getPropagationType() {
        return propagationType;
    }

    public void setPropagationType(PropagationType propagationType) {
        this.propagationType = propagationType;
    }

    public int getSimultaneousCircles() {
        return simultaneousCircles;
    }

    public void setSimultaneousCircles(int simultaneousCircles) {
        this.simultaneousCircles = simultaneousCircles;
    }

    public SphereType getSphereType() {
        return sphereType;
    }

    public void setSphereType(SphereType sphereType) {
        this.sphereType = sphereType;
    }

    @Override
    public Object clone() {
        Sphere obj = (Sphere) super.clone();
        return obj;
    }
}
