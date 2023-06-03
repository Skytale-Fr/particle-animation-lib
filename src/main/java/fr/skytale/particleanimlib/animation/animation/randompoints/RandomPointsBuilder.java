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
        animation.setSpeed(new Constant<>(0.1f));
        animation.setTicksDuration(300);
    }

    @Override
    protected RandomPoints initAnimation() {
        return new RandomPoints();
    }

    public void setNbPoints(int nbPoints) {
        animation.setNbPoints(nbPoints);
        checkNbPoints();
    }

    public void setRadius(float radius) {
        animation.setRadius(radius);
        checkRadius();
    }

    public void setDirectionChangePeriod(IVariable<Integer> directionChangePeriod) {
        animation.setDirectionChangePeriod(directionChangePeriod);
        checkDirectionChangePeriod();
    }

    public void setSpeed(IVariable<Float> speed) {
        animation.setSpeed(speed);
        checkSpeed();
    }

    @Override
    public RandomPoints getAnimation() {
        checkNbPoints();
        checkRadius();
        checkDirectionChangePeriod();
        checkSpeed();
        return super.getAnimation();
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
