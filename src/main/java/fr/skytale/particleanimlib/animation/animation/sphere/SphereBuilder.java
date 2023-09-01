package fr.skytale.particleanimlib.animation.animation.sphere;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;

public class SphereBuilder extends ARoundAnimationBuilder<Sphere, SphereTask> {

    public SphereBuilder() {
        super();
        animation.setRadius(2.0);
        animation.setNbPoints(80);
        animation.setType(Sphere.Type.FULL);
        animation.setShowPeriod(1);
        animation.setPropagationType(Sphere.PropagationType.TOP_TO_BOTTOM);
        animation.setPercentShownWhilePropagate(0.3f);
        animation.setPercentStepWhilePropagate(0.1f);
        animation.setTicksDuration(60);
    }

    @Override
    protected Sphere initAnimation() {
        return new Sphere();
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

    /*********SETTERS des éléments spécifiques a la sphere ***********/

    /**
     * Make the sphere appear progressively.
     * @param propagationType the type of propagation
     * @param percentShownWhilePropagate the percent of the sphere shown at a given time
     * @param percentStepWhilePropagate the step between 2 displays (a way to set the propagation speed)
     */
    public void setPropagation(Sphere.PropagationType propagationType, IVariable<Float> percentShownWhilePropagate, IVariable<Float> percentStepWhilePropagate) {
        checkPositive(percentShownWhilePropagate, "percentShownWhilePropagate should be positive.", false);
        checkPositive(percentStepWhilePropagate, "percentStepWhilePropagate should be positive.", false);
        animation.setPropagationType(propagationType);
        animation.setPercentShownWhilePropagate(percentShownWhilePropagate);
        animation.setPercentStepWhilePropagate(percentStepWhilePropagate);
    }

    /**
     * Make the sphere appear progressively.
     * @param propagationType the type of propagation
     * @param percentShownWhilePropagate the percent of the sphere shown at a given time
     * @param percentStepWhilePropagate the step between 2 displays (a way to set the propagation speed)
     */
    public void setPropagation(Sphere.PropagationType propagationType, float percentShownWhilePropagate, IVariable<Float> percentStepWhilePropagate) {
        setPropagation(propagationType, new Constant<>(percentShownWhilePropagate), percentStepWhilePropagate);
    }

    /**
     * Make the sphere appear progressively.
     * @param propagationType the type of propagation
     * @param percentShownWhilePropagate the percent of the sphere shown at a given time
     * @param percentStepWhilePropagate the step between 2 displays (a way to set the propagation speed)
     */
    public void setPropagation(Sphere.PropagationType propagationType, IVariable<Float> percentShownWhilePropagate, float percentStepWhilePropagate) {
        setPropagation(propagationType, percentShownWhilePropagate, new Constant<>(percentStepWhilePropagate));
    }

    /**
     * Make the sphere appear progressively.
     * @param propagationType the type of propagation
     * @param percentShownWhilePropagate the percent of the sphere shown at a given time
     * @param percentStepWhilePropagate the step between 2 displays (a way to set the propagation speed)
     */
    public void setPropagation(Sphere.PropagationType propagationType, float percentShownWhilePropagate, float percentStepWhilePropagate) {
        setPropagation(propagationType, new Constant<>(percentShownWhilePropagate), new Constant<>(percentStepWhilePropagate));
    }

    /**
     * Set the type of the sphere.
     * @param sphereType the type of the sphere
     */
    public void setSphereType(Sphere.Type sphereType) {
        checkNotNull(sphereType, "sphereType should not be null");
        animation.setType(sphereType);
    }
}
