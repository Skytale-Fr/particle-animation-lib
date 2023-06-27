package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public abstract class ARoundAnimationBuilder<T extends ARoundAnimation, K extends AAnimationTask<T>> extends AAnimationBuilder<T, K> {

    /**
     * Set the radius of the animation
     * @param radius the radius of the animation
     */
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    /**
     * Set the radius of the animation
     * @param radius the radius of the animation
     */
    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    /**
     * Set the number of points of the animation
     * @param nbPoints the number of points of the animation
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        animation.setNbPoints(nbPoints);
    }

    /**
     * Set the number of points of the animation
     * @param nbPoints the number of points of the animation
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    @Override
    public T getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }

    /**
     * Set the angle between each point of the animation
     * @param angleBetweenEachPoint the angle between each point of the animation
     */
    protected void checkAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        checkPositiveAndNotNull(angleBetweenEachPoint, "angleBetweenEachPoint should be positive", false);
    }
}
