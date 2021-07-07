package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;

public abstract class ARoundAnimationBuilder<T extends ARoundAnimation> extends AAnimationBuilder<T> {

    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        checkAngleBetweenEachPoint(angleBetweenEachPoint);
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
    }

    @Override
    public T getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkAngleBetweenEachPoint(animation.getAngleBetweenEachPoint());
        return super.getAnimation();
    }

    protected void checkAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        checkPositiveAndNotNull(angleBetweenEachPoint, "angleBetweenEachPoint should be positive", false);
    }
}
