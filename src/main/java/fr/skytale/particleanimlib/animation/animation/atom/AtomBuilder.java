package fr.skytale.particleanimlib.animation.animation.atom;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class AtomBuilder extends AAnimationBuilder<Atom> {
    private Atom atom;

    public AtomBuilder() {
        super();
        animation.setShowFrequency(new Constant<>(0));
        animation.setTicksDuration(60);
    }

    @Override
    protected Atom initAnimation() {
        return new Atom();
    }

    /*********SETTERS des éléments spécifiques de l'atome ***********/

    public void setSecondParticle(ParticleTemplate particle) {
        animation.setSecondParticle(particle);
    }

    public void setSphere(AAnimation sphere) {
        animation.setSphere(sphere);
    }

    public void setCircle1(AAnimation circle1) {
        animation.setCircle1(circle1);
    }

    public void setCircle2(AAnimation circle2) {
        animation.setCircle2(circle2);
    }

    public void setCircle3(AAnimation circle3) {
        animation.setCircle3(circle3);
    }

    public void setCallback(AnimationEndedCallback callback) {
        throw new IllegalArgumentException("Only primitive animations have callback functions.");
    }

    @Override
    public Atom getAnimation() {
        if (animation.getSecondParticle() == null) {
            throw new IllegalArgumentException("Second particle should not be null.");
        }

        if (animation.getSphere() == null)
            throw new IllegalArgumentException("Sphere animation of Atom animation has to be initialized.");

        if (animation.getCircle1() == null || animation.getCircle2() == null || animation.getCircle3() == null)
            throw new IllegalArgumentException("All three circle animations of Atom animation has to be initialized.");

        return super.getAnimation();
    }
}
