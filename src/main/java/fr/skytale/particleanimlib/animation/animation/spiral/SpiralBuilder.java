package fr.skytale.particleanimlib.animation.animation.spiral;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;
import org.bukkit.util.Vector;

public class SpiralBuilder extends ARoundAnimationBuilder<Spiral> {

    public SpiralBuilder() {
        super();
        animation.setDirection(AnimationDirection.fromMoveVector(new Constant<>(new Vector(0, 1, 0))));
        animation.setNbSpiral(new Constant<>(2));
        animation.setNbTrailingParticles(new Constant<>(1));
        animation.setCentralPointDefinition(null);
        animation.setRadius(new Constant<>(2.0));
        animation.setAngleBetweenEachPoint(new Constant<>(Math.toRadians(20)));
    }

    @Override
    protected Spiral initAnimation() {
        return new Spiral();
    }

    /*********SETTERS des éléments spécifiques a la spirale ***********/


    public void setDirection(AnimationDirection direction) {
        checkNotNull(direction, "direction should not be null");
        animation.setDirection(direction);
    }

    public void setNbSpiral(IVariable<Integer> nbSpiral) {
        checkPositiveAndNotNull(nbSpiral, "nbSpiral should be positive", false);
        animation.setNbSpiral(nbSpiral);
    }

    public void setNbSpiral(int nbSpiral) {
        setNbSpiral(new Constant<>(nbSpiral));
    }

    public void setNbTrailingParticles(IVariable<Integer> nbTrailingParticles) {
        checkPositiveAndNotNull(nbTrailingParticles, "nbTrailingParticles should be positive or equal to zero", true);
        animation.setNbTrailingParticles(nbTrailingParticles);
    }

    public void setNbTrailingParticles(int nbTrailingParticles) {
        setNbTrailingParticles(new Constant<>(nbTrailingParticles));
    }

    public void setCentralPointDefinition(APointDefinition centralParticle) {
        animation.setCentralPointDefinition(centralParticle);
    }

    public void setCentralPointDefinition(ParticleTemplate particleTemplate) {
        setCentralPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    @Override
    public Spiral getAnimation() {
        checkNotNull(animation.getDirection(), "direction should not be null");
        checkPositiveAndNotNull(animation.getNbSpiral(), "nbSpiral should be positive", false);
        checkPositiveAndNotNull(animation.getNbTrailingParticles(), "nbTrailingParticles should be positive or equal to zero", true);
        return super.getAnimation();
    }

    @Override
    protected void checkAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        //In the context of spirals, angleBetweenEachPoint can be negative, that's why we overrided the method.
        checkNotNullOrZero(angleBetweenEachPoint, "angleBetweenEachPoint should not be null");
    }
}
