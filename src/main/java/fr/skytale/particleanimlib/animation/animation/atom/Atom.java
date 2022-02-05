package fr.skytale.particleanimlib.animation.animation.atom;


import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public class Atom extends AAnimation implements ISubAnimation {
    private AAnimation sphere;
    private AAnimation circle1;
    private AAnimation circle2;
    private AAnimation circle3;
    private ParticleTemplate secondParticle;

    public Atom() {
    }

    @Override
    public AAnimationTask<? extends AAnimation> show() {
        sphere.show();
        circle1.show();
        circle2.show();
        return circle3.show();
        // TODO: Re-work Atom animation to have a AtomTask = Having a real Atom animation !!!!!
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
    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        this.sphere.addAnimationEndedCallback(callback);
    }


    @Override
    public Atom clone() {
        Atom obj = (Atom) super.clone();
        obj.circle1 = (AAnimation) circle1.clone();
        obj.circle2 = (AAnimation) circle2.clone();
        obj.circle3 = (AAnimation) circle3.clone();
        obj.sphere = (AAnimation) sphere.clone();
        obj.secondParticle = new ParticleTemplate(secondParticle);
        return obj;
    }
}
