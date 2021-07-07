package fr.skytale.particleanimlib.animation.atom;


import fr.skytale.particleanimlib.animation.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.AAnimation;
import fr.skytale.particleanimlib.animation.parent.ARoundAnimation;

public class Atom extends ARoundAnimation {
    private AAnimation sphere;
    private AAnimation circle1;
    private AAnimation circle2;
    private AAnimation circle3;
    private ParticleTemplate secondParticle;

    public Atom() {
    }

    @Override
    public void show() {
        sphere.show();
        circle1.show();
        circle2.show();
        circle3.show();
    }

    /***********GETTERS & SETTERS***********/

    public ParticleTemplate getSecondParticle() {
        return secondParticle;
    }

    public void setSecondParticle(ParticleTemplate secondParticle) {
        this.secondParticle = secondParticle;
    }

    public AAnimation getSphere() {
        return sphere;
    }

    public void setSphere(AAnimation sphere) {
        this.sphere = sphere;
    }

    public AAnimation getCircle1() {
        return circle1;
    }

    public void setCircle1(AAnimation circle1) {
        this.circle1 = circle1;
    }

    public AAnimation getCircle2() {
        return circle2;
    }

    public void setCircle2(AAnimation circle2) {
        this.circle2 = circle2;
    }

    public AAnimation getCircle3() {
        return circle3;
    }

    public void setCircle3(AAnimation circle3) {
        this.circle3 = circle3;
    }

    @Override
    public void setCallback(AnimationEndedCallback callback) {
        this.sphere.setCallback(callback);
    }


    @Override
    public Object clone() {
        Atom obj = (Atom) super.clone();
        obj.circle1 = (AAnimation) circle1.clone();
        obj.circle2 = (AAnimation) circle2.clone();
        obj.circle3 = (AAnimation) circle3.clone();
        obj.sphere = (AAnimation) sphere.clone();
        obj.secondParticle = new ParticleTemplate(secondParticle);
        return obj;
    }
}
