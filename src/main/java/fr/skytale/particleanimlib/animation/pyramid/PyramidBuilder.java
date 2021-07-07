package fr.skytale.particleanimlib.animation.pyramid;

import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class PyramidBuilder extends AAnimationBuilder<Pyramid> {

    public PyramidBuilder() {
        super();
        animation.setDistanceBetweenParticles(0.3);
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
        animation.setNbBaseApex(3);
        animation.setFromCenterToApex(new Vector(0, 5, 0));
        animation.setDistanceToAnyBaseApex(3);
    }

    @Override
    protected Pyramid initAnimation() {
        return new Pyramid();
    }

    /*********SETTERS des éléments spécifiques a la pyramide ***********/

    public void setFromCenterToApex(Vector centerToapex) {
        if (centerToapex == null)
            throw new IllegalArgumentException("Apex point of pyramid has to be defined.");
        animation.setFromCenterToApex(centerToapex);
    }

    public void setNbBaseApex(int nbBaseApex) {
        if (nbBaseApex <= 2)
            throw new IllegalArgumentException("A pyramid should have at least 3 base apex.");
        animation.setNbBaseApex(nbBaseApex);
    }

    public void setDistanceBetweenParticles(double stepBetweenParticle) {
        if (stepBetweenParticle <= 0)
            throw new IllegalArgumentException("The step value has to be strictly positive.");
        animation.setDistanceBetweenParticles(stepBetweenParticle);
    }

    public void setDistanceToAnyBaseApex(double distanceToAnyBaseApex) {
        if (distanceToAnyBaseApex <= 0.5)
            throw new IllegalArgumentException("The distance between the pyramid center and its base apex should be greater than 0.5.");
        animation.setDistanceToAnyBaseApex(distanceToAnyBaseApex);
    }

    @Override
    public Pyramid getAnimation() {
        return super.getAnimation();
    }
}
