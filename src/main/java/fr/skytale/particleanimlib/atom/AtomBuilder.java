package fr.skytale.particleanimlib.atom;

import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;

public class AtomBuilder extends AAnimationBuilder {
    private Atom atom;

    public AtomBuilder(){
        atom = new Atom();
    }

    /*********SETTERS des éléments spécifiques de l'atome ***********/

    public void setStepAngle(double a){
        atom.setStepAngle(a);
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
    public AAnimation getAnimation() {
        return atom;
    }
}
