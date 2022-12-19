package fr.skytale.particleanimlib.animation.animation.epi;


import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;

public class Epi extends AAnimation implements ISubAnimation {

    private IVariable<Integer> nbPoints;
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

    @Override
    public Epi clone() {
        Epi obj = (Epi) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.epiModifierNumerator = epiModifierNumerator.copy();
        obj.epiModifierDenominator = epiModifierDenominator.copy();
        obj.radius = radius.copy();
        obj.maxRadius = maxRadius != null ? maxRadius.copy() : maxRadius;
        return obj;
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

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }
}
