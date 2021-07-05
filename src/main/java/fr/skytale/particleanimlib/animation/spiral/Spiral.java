package fr.skytale.particleanimlib.animation.spiral;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;

public class Spiral extends ARoundAnimation {
    private AnimationDirection direction;
    private IVariable<Integer> nbSpiral;
    private IVariable<Integer> nbTrailingParticles;
    private ParticleTemplate centralParticle;

    public Spiral() {
    }

    @Override
    public void show() {
        new SpiralTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public AnimationDirection getDirection() {
        return direction;
    }

    public void setDirection(AnimationDirection direction) {
        this.direction = direction;
    }

    public IVariable<Integer> getNbSpiral() {
        return nbSpiral;
    }

    public void setNbSpiral(IVariable<Integer> nbSpiral) {
        this.nbSpiral = nbSpiral;
    }

    public IVariable<Integer> getNbTrailingParticles() {
        return nbTrailingParticles;
    }

    public void setNbTrailingParticles(IVariable<Integer> nbTrailingParticles) {
        this.nbTrailingParticles = nbTrailingParticles;
    }

    public ParticleTemplate getCentralParticle() {
        return centralParticle;
    }

    public void setCentralParticle(ParticleTemplate centralParticle) {
        this.centralParticle = centralParticle;
    }

    @Override
    public Spiral clone() {
        Spiral obj = (Spiral) super.clone();
        obj.direction = direction.clone();
        obj.nbSpiral = nbSpiral.copy();
        obj.centralParticle = new ParticleTemplate(this.centralParticle);
        obj.nbTrailingParticles = nbTrailingParticles.copy();
        return obj;
    }
}
