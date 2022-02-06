package fr.skytale.particleanimlib.animation.animation.sphere;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;

public class SphereBuilder extends ARoundAnimationBuilder<Sphere, SphereTask> {

    public SphereBuilder() {
        super();
        animation.setNbCircles(new Constant<Integer>(10));
        animation.setRadius(new Constant<>(2.0));
        animation.setAngleBetweenEachPoint(new Constant<>(Math.toRadians(30)));
        animation.setSphereType(Sphere.Type.FULL);
        animation.setShowPeriod(new Constant<>(1));
        animation.setPropagationType(null);
        animation.setTicksDuration(60);
    }

    @Override
    protected Sphere initAnimation() {
        return new Sphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere ***********/

    public void setNbCircles(IVariable<Integer> nbCircles) {
        checkPositiveAndNotNull(nbCircles, "The number of circles should be positive.", false);
        animation.setNbCircles(nbCircles);
    }

    public void setNbCircles(int nbCircles) {
        setNbCircles(new Constant<>(nbCircles));
    }

    public void setPropagation(Sphere.PropagationType propagationType, IVariable<Integer> propagationSimultaneousCircles) {
        checkPositive(propagationSimultaneousCircles, "propagationSimultaneousCircles should be positive.", false);
        animation.setPropagationType(propagationType);
        animation.setSimultaneousCircles(propagationSimultaneousCircles);
    }

    public void setPropagation(Sphere.PropagationType propagationType, int propagationSimultaneousCircles) {
        setPropagation(propagationType, new Constant<>(propagationSimultaneousCircles));
    }

    public void setSphereType(Sphere.Type sphereType) {
        checkNotNull(sphereType, "sphereType should not be null");
        animation.setSphereType(sphereType);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    @Override
    public Sphere getAnimation() {
        checkNotNull(animation.getSphereType(), "sphereType should not be null");
        checkPositiveAndNotNull(animation.getNbCircles(), "The number of circles should be positive.", false);
        if (animation.getPropagationType() != null) {
            checkPositiveAndNotNull(animation.getSimultaneousCircles(), "propagationSimultaneousCircles should be positive.", false);
            checkSuperior(animation.getNbCircles(), animation.getSimultaneousCircles(), "Propagation Simultaneous Circles should be inferior to the total number of circles for this sphere", false);
        }

        return super.getAnimation();
    }
}
