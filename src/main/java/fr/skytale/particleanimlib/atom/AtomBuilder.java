package fr.skytale.particleanimlib.atom;

import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class AtomBuilder extends AAnimationBuilder<Atom> {
    private Atom atom;

    public AtomBuilder(){
        atom = new Atom();
        atom.setStepAngle(15);
    }

    /*********SETTERS des éléments spécifiques de l'atome ***********/

    public void setStepAngle(double a){
        if(a==0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    public void setSecondParticle(ParticleTemplate particle){
        atom.setSecondParticle(particle);
    }

    public void setSphere(AAnimation sphere) {
        atom.setSphere(sphere);
    }

    public void setCircle1(AAnimation circle1) {
        atom.setCircle1(circle1);
    }

    public void setCircle2(AAnimation circle2) {
        atom.setCircle2(circle2);
    }

    public void setCircle3(AAnimation circle3) {
        atom.setCircle3(circle3);
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

        return atom;
    }
}
