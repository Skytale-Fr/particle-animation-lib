package fr.skytale.particleanimlib.animation.animation.rose;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.util.Vector;

public class Rose extends ARotatingAnimation implements ISubAnimation, ISubAnimationContainer {
    private IVariable<Integer> nbPoints;
    private APointDefinition pointDefinition;
    private IVariable<Double> roseModifierNumerator;
    private IVariable<Integer> roseModifierDenominator;
    private IVariable<Double> radius;

    public Rose() {
    }

    @Override
    public RoseTask show() {
        return new RoseTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Double> getRoseModifierNumerator() {
        return roseModifierNumerator;
    }

    public void setRoseModifierNumerator(IVariable<Double> roseModifierNumerator) {
        this.roseModifierNumerator = roseModifierNumerator;
    }

    public IVariable<Integer> getRoseModifierDenominator() {
        return roseModifierDenominator;
    }

    public void setRoseModifierDenominator(IVariable<Integer> roseModifierDenominator) {
        this.roseModifierDenominator = roseModifierDenominator;
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
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
    public Rose clone() {
        Rose obj = (Rose) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.pointDefinition = pointDefinition.clone();
        obj.roseModifierNumerator = roseModifierNumerator.copy();
        obj.roseModifierDenominator = roseModifierDenominator.copy();
        obj.radius = radius.copy();
        return obj;
    }
}
