package fr.skytale.particleanimlib.animation.explodingsphere;

import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;

public class ExplodingSphereBuilder extends AAnimationBuilder<ExplodingSphere> {

    public ExplodingSphereBuilder() {
        super();
        animation.setGrowthSpeed(0.3);
        animation.setExplosionLimit(6);
        animation.setNbCircles(10);
        animation.setRadius(1.0);
        animation.setStepAngle(Math.toRadians(30));
        animation.setShowFrequency(0);
        animation.setTicksDuration(60);
    }

    @Override
    protected ExplodingSphere initAnimation() {
        return new ExplodingSphere();
    }

    /*********SETTERS des éléments spécifiques a la sphere explosante ***********/

    public void setGrowthSpeed(double growthSpeed) {
        if (growthSpeed <= 0) {
            throw new IllegalArgumentException("Growth speed must be strictly positive.");
        }
        animation.setGrowthSpeed(growthSpeed);
    }

    public void setExplosionLimit(double explosionLimit) {
        if (explosionLimit <= 0)
            throw new IllegalArgumentException("Explosion limit must be strictly positive.");
        ;
        animation.setExplosionLimit(explosionLimit);
    }

    public void setNbCircles(int nbCircles) {
        if (nbCircles <= 0)
            throw new IllegalArgumentException("Number of circles must be positive.");
        animation.setNbCircles(nbCircles);
    }

    public void setRadius(double radius) {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("Radius should be positive.");
        }
        animation.setRadius(radius);
    }

    public void setStepAngle(double a) {
        if (a == 0)
            throw new IllegalArgumentException("Step angle should not be equal to zero.");
        animation.setStepAngle(a);
    }

    @Override
    public ExplodingSphere getAnimation() {
        return super.getAnimation();
    }

}
