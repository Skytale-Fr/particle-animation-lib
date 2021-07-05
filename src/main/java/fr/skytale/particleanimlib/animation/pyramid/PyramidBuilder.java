package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class PyramidBuilder extends AAnimationBuilder<Pyramid> {

    public PyramidBuilder() {
        super();
        animation.setDistanceBetweenParticles(new Constant<>(0.3));
        animation.setShowFrequency(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setNbBaseApex(new Constant<>(3));
        animation.setFromCenterToApex(new Constant<>(new Vector(0, 5, 0)));
        animation.setDistanceToAnyBaseApex(new Constant<>(3.0));
    }

    @Override
    protected Pyramid initAnimation() {
        return new Pyramid();
    }

    /********* Pyramid specific setters ***********/

    public void setFromCenterToApex(IVariable<Vector> fromCenterToApex) {
        if (fromCenterToApex == null)
            throw new IllegalArgumentException("Apex point of pyramid has to be defined.");
        animation.setFromCenterToApex(fromCenterToApex);
    }

    public void setNbBaseApex(IVariable<Integer> nbBaseApex) {
        if (nbBaseApex.isConstant() && nbBaseApex.getCurrentValue(0) <= 2)
            throw new IllegalArgumentException("A pyramid should have at least 3 base apex.");
        animation.setNbBaseApex(nbBaseApex);
    }

    public void setDistanceBetweenParticles(IVariable<Double> distanceBetweenParticles) {
        if (distanceBetweenParticles.isConstant() && distanceBetweenParticles.getCurrentValue(0) <= 0)
            throw new IllegalArgumentException("The distanceBetweenParticles has to be strictly positive.");
        animation.setDistanceBetweenParticles(distanceBetweenParticles);
    }

    public void setDistanceToAnyBaseApex(IVariable<Double> distanceToAnyBaseApex) {
        if (distanceToAnyBaseApex.isConstant() && distanceToAnyBaseApex.getCurrentValue(0) <= 0.5)
            throw new IllegalArgumentException("The distance between the pyramid center and its base apexes (distanceToAnyBaseApex) should be greater than 0.5.");
        animation.setDistanceToAnyBaseApex(distanceToAnyBaseApex);
    }

    @Override
    public Pyramid getAnimation() {
        return super.getAnimation();
    }
}
