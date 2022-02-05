package fr.skytale.particleanimlib.animation.animation.spiral;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;

public class Spiral extends ARoundAnimation implements IDirectionSubAnimation, ISubAnimationContainer {
    private AnimationDirection direction;
    private IVariable<Integer> nbSpiral;
    private IVariable<Integer> nbTrailingParticles;
    private APointDefinition centralPointDefinition;
    private APointDefinition pointDefinition;

    public Spiral() {
    }

    @Override
    public SpiralTask show() {
        return new SpiralTask(this);
    }

    /***********GETTERS & SETTERS***********/

    @Override
    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
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

    public APointDefinition getCentralPointDefinition() {
        return centralPointDefinition;
    }

    public void setCentralPointDefinition(APointDefinition centralPointDefinition) {
        this.centralPointDefinition = centralPointDefinition;
    }

    @Override
    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    @Override
    public void setPointDefinition(APointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
    }

    @Override
    public ParticleTemplate getMainParticle() {
        if (this.pointDefinition instanceof ParticlePointDefinition) {
            return ((ParticlePointDefinition) pointDefinition).getParticleTemplate();
        }
        throw new IllegalStateException("ParticleTemplate is not defined since this animation PointDefinition defines a sub animation");
    }

    @Override
    public void setMainParticle(ParticleTemplate mainParticle) {
        setPointDefinition(APointDefinition.fromParticleTemplate(mainParticle));
    }

    @Override
    public Spiral clone() {
        Spiral obj = (Spiral) super.clone();
        obj.direction = direction.clone();
        obj.nbSpiral = nbSpiral.copy();
        obj.nbTrailingParticles = nbTrailingParticles.copy();
        obj.pointDefinition = pointDefinition.clone();
        obj.centralPointDefinition = centralPointDefinition == null ? null : centralPointDefinition.clone();
        return obj;
    }
}
