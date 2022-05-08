package fr.skytale.particleanimlib.animation.animation.sphere;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;


public class Sphere extends ARoundAnimation implements ISubAnimation, ISubAnimationContainer {

    private IVariable<Integer> nbCircles;
    private PropagationType propagationType = null;
    private IVariable<Integer> simultaneousCircles;
    private Type type;
    private APointDefinition pointDefinition;

    public Sphere() {
    }

    @Override
    public SphereTask show() {
        return new SphereTask(this);
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
    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    @Override
    public void setPointDefinition(APointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
    }

    @Override
    public ParticleTemplate getMainParticle() {
        if (this.pointDefinition instanceof ParticlePointDefinition) {
            return ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
        }
        throw new IllegalStateException("ParticleTemplate is not defined since this animation PointDefinition defines a sub animation");
    }

    @Override
    public void setMainParticle(ParticleTemplate mainParticle) {
        setPointDefinition(APointDefinition.fromParticleTemplate(mainParticle));
    }

    @Override
    public Sphere clone() {
        Sphere obj = (Sphere) super.clone();
        obj.nbCircles = nbCircles.copy();
        obj.simultaneousCircles = simultaneousCircles == null ? null : simultaneousCircles.copy();
        obj.pointDefinition = pointDefinition.clone();
        return obj;
    }

    public enum PropagationType {
        TOP_TO_BOTTOM, BOTTOM_TO_TOP;
    }

    public enum Type {
        FULL, HALF_TOP, HALF_BOTTOM;
    }
}
