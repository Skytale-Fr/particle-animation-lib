package fr.skytale.particleanimlib.animation.animation.sphere;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;

public class SphereBuilder extends ARoundAnimationBuilder<Sphere, SphereTask> {

    public SphereBuilder() {
        super();
        animation.setRadius(new Constant<>(2.0));
        animation.setNbPoints(new Constant<>(80));
        animation.setType(Sphere.Type.FULL);
        animation.setShowPeriod(new Constant<>(1));
        animation.setPropagationType(null);
        animation.setPercentShownWhilePropagate(new Constant<>(0.3f));
        animation.setPercentStepWhilePropagate(new Constant<>(0.1f));
        animation.setTicksDuration(60);
    }

    @Override
    protected Sphere initAnimation() {
        return new Sphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere ***********/

    public void setPropagation(Sphere.PropagationType propagationType, IVariable<Float> percentShownWhilePropagate, IVariable<Float> percentStepWhilePropagate) {
        checkPositive(percentShownWhilePropagate, "percentShownWhilePropagate should be positive.", false);
        checkPositive(percentStepWhilePropagate, "percentStepWhilePropagate should be positive.", false);
        animation.setPropagationType(propagationType);
        animation.setPercentShownWhilePropagate(percentShownWhilePropagate);
        animation.setPercentStepWhilePropagate(percentStepWhilePropagate);
    }

    public void setPropagation(Sphere.PropagationType propagationType, float percentShownWhilePropagate, IVariable<Float> percentStepWhilePropagate) {
        setPropagation(propagationType, new Constant<>(percentShownWhilePropagate), percentStepWhilePropagate);
    }

    public void setPropagation(Sphere.PropagationType propagationType, IVariable<Float> percentShownWhilePropagate, float percentStepWhilePropagate) {
        setPropagation(propagationType, percentShownWhilePropagate, new Constant<>(percentStepWhilePropagate));
    }


    public void setPropagation(Sphere.PropagationType propagationType, float percentShownWhilePropagate, float percentStepWhilePropagate) {
        setPropagation(propagationType, new Constant<>(percentShownWhilePropagate), new Constant<>(percentStepWhilePropagate));
    }

    public void setSphereType(Sphere.Type sphereType) {
        checkNotNull(sphereType, "sphereType should not be null");
        animation.setType(sphereType);
    }

    @Override
    public Sphere getAnimation() {
        checkNotNull(animation.getType(), "sphereType should not be null");
        if (animation.getPropagationType() != null) {
            checkPositive(animation.getPercentShownWhilePropagate(), "percentShownWhilePropagate should be positive.", false);
            checkPositive(animation.getPercentStepWhilePropagate(), "percentStepWhilePropagate should be positive.", false);
        }

        return super.getAnimation();
    }
}
