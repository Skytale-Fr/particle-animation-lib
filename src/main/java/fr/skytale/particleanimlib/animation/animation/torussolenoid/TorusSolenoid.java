package fr.skytale.particleanimlib.animation.animation.torussolenoid;


import fr.skytale.particleanimlib.animation.animation.rose.RoseTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IPlaneSubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimationContainer;
import org.bukkit.util.Vector;

public class TorusSolenoid extends ARotatingAnimation implements ISubAnimation, ISubAnimationContainer {
    private IVariable<Integer> nbPoints;
    private APointDefinition pointDefinition;
    private IVariable<Double> torusSolenoidModifierNumerator;
    private IVariable<Integer> torusSolenoidModifierDenominator;
    private IVariable<Double> torusRadius;
    private IVariable<Double> solenoidRadius;

    public TorusSolenoid() {
    }

    @Override
    public TorusSolenoidTask show() {
        return new TorusSolenoidTask(this);
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

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getTorusRadius() {
        return torusRadius;
    }

    public void setTorusRadius(IVariable<Double> maxRadius) {
        this.torusRadius = maxRadius;
    }

    public IVariable<Double> getSolenoidRadius() {
        return solenoidRadius;
    }

    public void setSolenoidRadius(IVariable<Double> solenoidRadius) {
        this.solenoidRadius = solenoidRadius;
    }

    public IVariable<Double> getTorusSolenoidModifierNumerator() {
        return torusSolenoidModifierNumerator;
    }

    public void setTorusSolenoidModifierNumerator(IVariable<Double> torusSolenoidModifierNumerator) {
        this.torusSolenoidModifierNumerator = torusSolenoidModifierNumerator;
    }

    public IVariable<Integer> getTorusSolenoidModifierDenominator() {
        return torusSolenoidModifierDenominator;
    }

    public void setTorusSolenoidModifierDenominator(IVariable<Integer> torusSolenoidModifierDenominator) {
        this.torusSolenoidModifierDenominator = torusSolenoidModifierDenominator;
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
    public TorusSolenoid clone() {
        TorusSolenoid obj = (TorusSolenoid) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.pointDefinition = pointDefinition.clone();
        obj.torusSolenoidModifierNumerator = torusSolenoidModifierNumerator.copy();
        obj.torusSolenoidModifierDenominator = torusSolenoidModifierDenominator.copy();
        obj.solenoidRadius = solenoidRadius.copy();
        obj.torusRadius = torusRadius.copy();
        return obj;
    }
}
