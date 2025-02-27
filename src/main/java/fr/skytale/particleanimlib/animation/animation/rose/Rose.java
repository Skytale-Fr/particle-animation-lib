package fr.skytale.particleanimlib.animation.animation.rose;


import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class Rose extends AAnimation {
    private IVariable<Integer> nbPoints;
    private IVariable<Double> roseModifierNumerator;
    private IVariable<Integer> roseModifierDenominator;
    private IVariable<Double> radius;

    public Rose() {
    }

    @Override
    public RoseTask show() {
        return new RoseTask(this);
    }

    @Override
    public Rose clone() {
        Rose obj = (Rose) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.roseModifierNumerator = roseModifierNumerator.copy();
        obj.roseModifierDenominator = roseModifierDenominator.copy();
        obj.radius = radius.copy();
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public IVariable<Double> getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        setRadius(new Constant<>(radius));
    }

    public void setRadius(IVariable<Double> radius) {
        this.radius = radius;
    }

    public IVariable<Double> getRoseModifierNumerator() {
        return roseModifierNumerator;
    }

    public void setRoseModifierNumerator(Double roseModifierNumerator) {
        setRoseModifierNumerator(new Constant<>(roseModifierNumerator));
    }

    public void setRoseModifierNumerator(IVariable<Double> roseModifierNumerator) {
        this.roseModifierNumerator = roseModifierNumerator;
    }

    public IVariable<Integer> getRoseModifierDenominator() {
        return roseModifierDenominator;
    }

    public void setRoseModifierDenominator(Integer roseModifierDenominator) {
        setRoseModifierDenominator(new Constant<>(roseModifierDenominator));
    }

    public void setRoseModifierDenominator(IVariable<Integer> roseModifierDenominator) {
        this.roseModifierDenominator = roseModifierDenominator;
    }

    public IVariable<Integer> getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(Integer nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        this.nbPoints = nbPoints;
    }
}
