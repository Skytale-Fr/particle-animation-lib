package fr.skytale.particleanimlib.animation.atom;

import fr.skytale.particleanimlib.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class AtomBuilder extends AAnimationBuilder<Atom> {
    private Atom atom;

    public AtomBuilder(){
        super();
        animation = new Atom();
        animation.setStepAngle(Math.toRadians(15));
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    /*********SETTERS des éléments spécifiques de l'atome ***********/

    public void setStepAngle(double a){
        if(a==0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    public void setSecondParticle(ParticleTemplate particle){
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

        if(animation.getSphere()==null)
            throw  new IllegalArgumentException("Sphere animation of Atom animation has to be initialized.");

        if(animation.getCircle1()==null ||animation.getCircle2()==null || animation.getCircle3()==null)
            throw  new IllegalArgumentException("All three circle animations of Atom animation has to be initialized.");

        return super.getAnimation();
    }
}
