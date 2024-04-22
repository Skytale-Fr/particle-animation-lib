package fr.skytale.particleanimlib.animation.animation.wave;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class WaveBuilder extends AAnimationBuilder<Wave, WaveTask> {

    public WaveBuilder() {
        animation.setRadiusStart(1.0);
        animation.setRadiusMax(20);
        animation.setNbPoints(new Constant<>(20));
        animation.setRadiusStep(new CallbackVariable<>(iterationCount -> 0.3 + Math.sin(iterationCount) / 4));
        animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / 20));
        animation.setShowPeriod(new Constant<>(0));
        animation.setTicksDuration(60);
        animation.setPositiveHeight(true);
    }

    @Override
    protected Wave initAnimation() {
        return new Wave();
    }

    @Override
    public Wave getAnimation() {
        if (animation.getRadiusStart() <= 0) {
            throw new IllegalArgumentException("RadiusStart should be positive.");
        }
        if (animation.getRadiusMax() <= 0) {
            throw new IllegalArgumentException("RadiusMax should be positive.");
        }
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        checkPositiveAndNotNull(animation.getAngleBetweenEachPoint(), "angleBetweenEachPoint should be positive", false);
        return super.getAnimation();
    }

    /********* Circle specific setters ***********/

    /**
     * Defines the start radius of the wave
     * @param radiusStart initial radius of the wave
     */
    public void setRadiusStart(double radiusStart) {
        if (radiusStart <= 0) {
            throw new IllegalArgumentException("RadiusStart should be positive.");
        }
        animation.setRadiusStart(radiusStart);
    }


    /**
     * Defines how the radius will evolve during the animation
     * @param radiusStep the radius step
     */
    public void setRadiusStep(double radiusStep) {
        setRadiusStep(new Constant<>(radiusStep));
    }

    /**
     * Defines how the radius will evolve during the animation
     * @param radiusStep the radius step
     */
    public void setRadiusStep(IVariable<Double> radiusStep) {
        animation.setRadiusStep(radiusStep);
    }

    public void setRadiusMax(double radiusMax) {
        if (radiusMax <= 0) {
            throw new IllegalArgumentException("RadiusMax should be positive.");
        }
        animation.setRadiusMax(radiusMax);
    }

    /**
     * Defines the number of radian between each point of each wave's circle
     * @param angleBetweenEachPoint the angle between each point
     */
    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        setAngleBetweenEachPoint(angleBetweenEachPoint, false);
    }

    /**
     * Defines the number of radian between each point of each wave's circle
     * @param angleBetweenEachPoint the angle between each point
     */
    public void setAngleBetweenEachPoint(double angleBetweenEachPoint) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint));
    }

    /**
     * Defines the number of radian between each point of each wave's circle
     * @param angleBetweenEachPoint the angle between each point
     * @param fullCircle if true, the angleBetweenEachPoint will be ignored and the circle will be full
     */
    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint, boolean fullCircle) {
        checkPositiveAndNotNull(angleBetweenEachPoint, "angleBetweenEachPoint should be positive", false);
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
        if (fullCircle) {
            if (!angleBetweenEachPoint.isConstant())
                throw new IllegalArgumentException(CircleBuilder.FULL_CIRCLE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE);
            animation.setNbPoints(new Constant<>((int) Math.round(
                    2 * Math.PI / angleBetweenEachPoint.getCurrentValue(0))));
        }
    }

    /**
     * Defines the number of radian between each point of each wave's circle
     * @param angleBetweenEachPoint the angle between each point
     * @param fullCircle if true, the number of points will be computed automatically to make a full circle
     */
    public void setAngleBetweenEachPoint(double angleBetweenEachPoint, boolean fullCircle) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint), fullCircle);
    }

    /**
     * Defines the number of points in each wave's circle
     * @param nbPoints the number of points in each wave's circle
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        setNbPoints(nbPoints, false);
    }

    /**
     * Defines the number of points in each wave's circle
     * @param nbPoints the number of points in each wave's circle
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    /**
     * Defines the number of points in each wave's circle
     * @param nbPoints the number of points in each wave's circle
     * @param fullCircle if true, the angle between each points will be computed automatically to make a full circle
     */
    public void setNbPoints(IVariable<Integer> nbPoints, boolean fullCircle) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        if (fullCircle) {
            if (!nbPoints.isConstant())
                throw new IllegalArgumentException(CircleBuilder.FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE);
            animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / nbPoints.getCurrentValue(0)));
        }
    }

    /**
     * Defines the number of points in each wave's circle
     * @param nbPoints the number of points in each wave's circle
     * @param fullCircle if true, the angle between each points will be computed automatically to make a full circle
     */
    public void setNbPoints(int nbPoints, boolean fullCircle) {
        setNbPoints(new Constant<>(nbPoints), fullCircle);
    }

    /**
     * Defines if the wave will start at the top of its height or at the bottom
     * @param positiveHeight if true, the wave will start at the top of its height
     */
    public void setPositiveHeight(boolean positiveHeight) {
        animation.setPositiveHeight(positiveHeight);
    }
}
