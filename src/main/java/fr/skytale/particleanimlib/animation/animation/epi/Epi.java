package fr.skytale.particleanimlib.animation.animation.epi;


import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.util.Vector;

public class Epi extends ARotatingAnimation implements IPlaneSubAnimation, ISubAnimationContainer {
    private Vector u;
    private Vector v;
    private IVariable<Integer> nbPoints;
    private APointDefinition pointDefinition;
    private IVariable<Double> epiModifierNumerator;
    private IVariable<Integer> epiModifierDenominator;
    private IVariable<Double> radius;
    private IVariable<Double> maxRadius;

    public Epi() {
    }

    @Override
    public EpiTask show() {
        return new EpiTask(this);
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Double> getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(IVariable<Double> maxRadius) {
        this.maxRadius = maxRadius;
    }

    public IVariable<Double> getEpiModifierNumerator() {
        return epiModifierNumerator;
    }

    public void setEpiModifierNumerator(IVariable<Double> epiModifierNumerator) {
        this.epiModifierNumerator = epiModifierNumerator;
    }

    public IVariable<Integer> getEpiModifierDenominator() {
        return epiModifierDenominator;
    }

    public void setEpiModifierDenominator(IVariable<Integer> epiModifierDenominator) {
        this.epiModifierDenominator = epiModifierDenominator;
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
    public Epi clone() {
        Epi obj = (Epi) super.clone();
        obj.u = u.clone();
        obj.v = v.clone();
        obj.nbPoints = nbPoints.copy();
        obj.pointDefinition = pointDefinition.clone();
        obj.epiModifierNumerator = epiModifierNumerator.copy();
        obj.epiModifierDenominator = epiModifierDenominator.copy();
        obj.radius = radius.copy();
        obj.maxRadius = maxRadius != null ? maxRadius.copy() : maxRadius;
        return obj;
    }
}
