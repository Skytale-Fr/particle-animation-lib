package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingRoundAnimation;

public abstract class ARotatingRoundAnimationBuilder<T extends ARotatingRoundAnimation> extends ARotatingAnimationBuilder<T> {

    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        checkPositiveAndNotNull(angleBetweenEachPoint, "angleBetweenEachPoint should be positive", false);
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
    }

    @Override
    public T getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkPositiveAndNotNull(animation.getAngleBetweenEachPoint(), "angleBetweenEachPoint should be positive", false);
        return super.getAnimation();
    }
}
