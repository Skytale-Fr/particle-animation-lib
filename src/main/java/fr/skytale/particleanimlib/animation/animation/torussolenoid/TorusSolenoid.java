package fr.skytale.particleanimlib.animation.animation.torussolenoid;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class TorusSolenoid extends AAnimation {
    private IVariable<Integer> nbPoints;
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
    public TorusSolenoid clone() {
        TorusSolenoid obj = (TorusSolenoid) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.torusSolenoidModifierNumerator = torusSolenoidModifierNumerator.copy();
        obj.torusSolenoidModifierDenominator = torusSolenoidModifierDenominator.copy();
        obj.torusRadius = torusRadius.copy();
        obj.solenoidRadius = solenoidRadius.copy();
        return obj;
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
}
