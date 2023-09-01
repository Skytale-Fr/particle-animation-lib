package fr.skytale.particleanimlib.animation.animation.sphere;


import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;


public class Sphere extends ARoundAnimation {

    private PropagationType propagationType = null;
    private IVariable<Float> percentShownWhilePropagate;
    private IVariable<Float> percentStepWhilePropagate;
    private Type type;

    public Sphere() {
    }

    @Override
    public SphereTask show() {
        return new SphereTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public IVariable<Float> getPercentShownWhilePropagate() {
        return percentShownWhilePropagate;
    }

    public void setPercentShownWhilePropagate(Float percentShownWhilePropagate) {
        setPercentShownWhilePropagate(new Constant<>(percentShownWhilePropagate));
    }

    public void setPercentShownWhilePropagate(IVariable<Float> percentShownWhilePropagate) {
        this.percentShownWhilePropagate = percentShownWhilePropagate;
    }

    public IVariable<Float> getPercentStepWhilePropagate() {
        return percentStepWhilePropagate;
    }

    public void setPercentStepWhilePropagate(Float percentStepWhilePropagate) {
        setPercentStepWhilePropagate(new Constant<>(percentStepWhilePropagate));
    }

    public void setPercentStepWhilePropagate(IVariable<Float> percentStepWhilePropagate) {
        this.percentStepWhilePropagate = percentStepWhilePropagate;
    }

    public PropagationType getPropagationType() {
        return propagationType;
    }

    public void setPropagationType(PropagationType propagationType) {
        this.propagationType = propagationType;
    }

    @Override
    public Sphere clone() {
        Sphere obj = (Sphere) super.clone();
        obj.percentShownWhilePropagate = percentShownWhilePropagate.copy();
        return obj;
    }

    public enum PropagationType {
        TOP_TO_BOTTOM, BOTTOM_TO_TOP;
    }

    public enum Type {
        FULL, HALF_TOP, HALF_BOTTOM;
    }
}
