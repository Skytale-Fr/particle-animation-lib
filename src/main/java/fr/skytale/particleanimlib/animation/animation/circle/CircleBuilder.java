package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;

public class CircleBuilder extends ARoundAnimationBuilder<Circle, CircleTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_CIRCLE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE =
            "To do a fullCircle, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full circle) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE =
            "To do a fullCircle, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full circle) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";

    public CircleBuilder() {
        super();
        animation.setRadius(new Constant<>(3.0));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20), true);
    }

    @Override
    protected Circle initAnimation() {
        return new Circle();
    }

    @Override
    public Circle getAnimation() {
        checkAngleBetweenEachPoint(animation.getAngleBetweenEachPoint());
        return super.getAnimation();
    }

    /********* Circle specific setters ***********/

    /**
     * Defines the number of point of the circle
     * @param nbPoints the number of point of the circle
     */
    @Override
    public void setNbPoints(IVariable<Integer> nbPoints) {
        setNbPoints(nbPoints, false);
    }

    /**
     * Defines the number of point of the circle
     * @param nbPoints the number of point of the circle
     * @param fullCircle if true, the angle between each point will be computed to make a full circle
     */
    public void setNbPoints(int nbPoints, boolean fullCircle) {
        setNbPoints(new Constant<>(nbPoints), fullCircle);
    }

    /**
     * Defines the number of point of the circle
     * @param nbPoints the number of point of the circle
     * @param fullCircle if true, the angle between each point will be computed to make a full circle
     */
    public void setNbPoints(IVariable<Integer> nbPoints, boolean fullCircle) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        if (fullCircle) {
            if (!nbPoints.isConstant()) throw new IllegalArgumentException(FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE);
            animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / nbPoints.getCurrentValue(0)));
        }
    }

    /**
     * Defines the angle in radian between each point of the circle
     * @param angleBetweenEachPoint the angle in radian between each point of the circle
     */
    public void setAngleBetweenEachPoint(double angleBetweenEachPoint) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint));
    }

    /**
     * Defines the angle in radian between each point of the circle
     * @param angleBetweenEachPoint the angle in radian between each point of the circle
     */
    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        setAngleBetweenEachPoint(angleBetweenEachPoint, false);
    }

    /**
     * Defines the angle in radian between each point of the circle
     * @param angleBetweenEachPoint the angle in radian between each point of the circle
     * @param fullCircle if true, the number of point will be computed to make a full circle
     */
    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint, boolean fullCircle) {
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
        if (fullCircle) {
            if (!angleBetweenEachPoint.isConstant()) {
                final IVariable<Double> angleBetweenEachPointClone = angleBetweenEachPoint.copy();
                animation.setNbPoints(new CallbackVariable<>(iterationCount ->
                        (int) Math.round(2 * Math.PI / angleBetweenEachPointClone.getCurrentValue(iterationCount))
                ));
            } else {
                final int nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint.getCurrentValue(0));
                animation.setNbPoints(new Constant<>(nbPoints));
            }
        }
    }

    /**
     * Defines the angle in radian between each point of the circle
     * @param angleBetweenEachPoint the angle in radian between each point of the circle
     * @param fullCircle if true, the number of point will be computed to make a full circle
     */
    public void setAngleBetweenEachPoint(double angleBetweenEachPoint, boolean fullCircle) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint), fullCircle);
    }
}
