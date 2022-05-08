package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public abstract class ARoundAnimationBuilder<T extends ARoundAnimation, K extends AAnimationTask<T>> extends AAnimationBuilder<T, K> {

    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        checkAngleBetweenEachPoint(angleBetweenEachPoint);
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
    }

    public void setAngleBetweenEachPoint(double angleBetweenEachPoint) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint));
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
