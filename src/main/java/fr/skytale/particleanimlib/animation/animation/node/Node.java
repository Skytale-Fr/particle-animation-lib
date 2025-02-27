package fr.skytale.particleanimlib.animation.animation.node;


import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class Node extends AAnimation {
    private IVariable<Integer> nbPoints;
    private IVariable<Double> nodeModifierNumerator;
    private IVariable<Integer> nodeModifierDenominator;
    private IVariable<Double> radius;
    private IVariable<Double> maxRadius;

    public Node() {
    }

    @Override
    public NodeTask show() {
        return new NodeTask(this);
    }

    @Override
    public Node clone() {
        Node obj = (Node) super.clone();
        obj.nbPoints = nbPoints.copy();
        obj.nodeModifierNumerator = nodeModifierNumerator.copy();
        obj.nodeModifierDenominator = nodeModifierDenominator.copy();
        obj.radius = radius.copy();
        obj.maxRadius = maxRadius != null ? maxRadius.copy() : null;
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

    public IVariable<Double> getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(IVariable<Double> maxRadius) {
        this.maxRadius = maxRadius;
    }

    public IVariable<Double> getNodeModifierNumerator() {
        return nodeModifierNumerator;
    }

    public void setNodeModifierNumerator(Double nodeModifierNumerator) {
        setNodeModifierNumerator(new Constant<>(nodeModifierNumerator));
    }

    public void setNodeModifierNumerator(IVariable<Double> nodeModifierNumerator) {
        this.nodeModifierNumerator = nodeModifierNumerator;
    }

    public IVariable<Integer> getNodeModifierDenominator() {
        return nodeModifierDenominator;
    }

    public void setNodeModifierDenominator(Integer nodeModifierDenominator) {
        setNodeModifierDenominator(new Constant<>(nodeModifierDenominator));
    }

    public void setNodeModifierDenominator(IVariable<Integer> nodeModifierDenominator) {
        this.nodeModifierDenominator = nodeModifierDenominator;
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
