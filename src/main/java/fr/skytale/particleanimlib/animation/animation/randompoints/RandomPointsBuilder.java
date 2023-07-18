package fr.skytale.particleanimlib.animation.animation.randompoints;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class RandomPointsBuilder extends AAnimationBuilder<RandomPoints, RandomPointsTask> {

    public RandomPointsBuilder() {
        super();
        animation.setNbPoints(20);
        animation.setRadius(10);
        animation.setDirectionChangePeriod(new Constant<>(60));
        animation.setSpeed(new Constant<>(0.1d));
        animation.setTicksDuration(300);
    }

    @Override
    protected RandomPoints initAnimation() {
        return new RandomPoints();
    }

    @Override
    public RandomPoints getAnimation() {
        checkNbPoints();
        checkRadius();
        checkDirectionChangePeriod();
        checkSpeed();
        return super.getAnimation();
    }

    /********* Pyramid specific setters ***********/

    /**
     * Set the number of points that will spawn
     * @param nbPoints the number of points that will spawn
     */
    public void setNbPoints(int nbPoints) {
        animation.setNbPoints(nbPoints);
        checkNbPoints();
    }

    /**
     * Set the radius of the sphere where the points will spawn
     * @param radius the radius of the sphere where the points will spawn
     */
    public void setRadius(float radius) {
        animation.setRadius(radius);
        checkRadius();
    }

    /**
     * Set the period of time in ticks between each direction change of the points
     * @param directionChangePeriod the period of time in ticks between each direction change of the points
     */
    public void setDirectionChangePeriod(IVariable<Integer> directionChangePeriod) {
        animation.setDirectionChangePeriod(directionChangePeriod);
        checkDirectionChangePeriod();
    }

    /**
     * Set the period of time in ticks between each direction change of the points
     * @param directionChangePeriod the period of time in ticks between each direction change of the points
     */
    public void setDirectionChangePeriod(int directionChangePeriod) {
        setDirectionChangePeriod(new Constant<>(directionChangePeriod));
    }

    /**
     * Set the speed of the points
     * @param speed the speed of the points
     */
    public void setSpeed(IVariable<Double> speed) {
        animation.setSpeed(speed);
        checkSpeed();
    }

    /**
     * Set the speed of the points
     * @param speed the speed of the points
     */
    public void setSpeed(Double speed) {
        setSpeed(new Constant<>(speed));
    }

    private void checkNbPoints() {
        if (animation.getNbPoints() <= 0) {
            throw new IllegalArgumentException("The nbPoints must be positive");
        }
    }

    private void checkRadius() {
        if (animation.getRadius() <= 0) {
            throw new IllegalArgumentException("The radius must be positive");
        }
    }

    private void checkDirectionChangePeriod() {
        checkPositiveAndNotNull(animation.getDirectionChangePeriod(), "The directionChangePeriod must be positive or equal to zero", true);
    }

    private void checkSpeed() {
        checkNotNull(animation.getSpeed(), "The speed must not be null");
    }
}
