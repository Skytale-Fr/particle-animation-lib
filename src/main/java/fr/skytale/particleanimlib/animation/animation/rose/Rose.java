package fr.skytale.particleanimlib.animation.animation.rose;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingRoundAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.util.Vector;

public class Rose extends ARotatingAnimation implements IPlaneSubAnimation, ISubAnimationContainer {
    private Vector u;
    private Vector v;
    private IVariable<Integer> nbPoints;
    private APointDefinition pointDefinition;
    private IVariable<Double> roseModifierNumerator;
    private IVariable<Integer> roseModifierDenominator;
    protected IVariable<Double> radius;

    public Rose() {
    }

    @Override
    public void show() {
        new RoseTask(this);
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

    @Override
    public Vector getU() {
        return u;
    }

    @Override
    public void setU(Vector u) {
        this.u = u;
    }

    @Override
    public Vector getV() {
        return v;
    }

    @Override
    public void setV(Vector v) {
        this.v = v;
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
        obj.u = u.clone();
        obj.v = v.clone();
        obj.nbPoints = nbPoints.copy();
        obj.pointDefinition = pointDefinition.clone();
        obj.roseModifierNumerator = roseModifierNumerator.copy();
        obj.roseModifierDenominator = roseModifierDenominator.copy();
        obj.radius = radius.copy();
        return obj;
    }
}
