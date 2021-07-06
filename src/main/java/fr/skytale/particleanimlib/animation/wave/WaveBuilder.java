package fr.skytale.particleanimlib.animation.wave;

import fr.skytale.particleanimlib.animation.attributes.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class WaveBuilder extends AAnimationBuilder<Wave> {

    public WaveBuilder() {
        animation.setRadiusStart(1.0);
        animation.setRadiusMax(20);
        animation.setNbPoints(new Constant<>(20));
        animation.setRadiusStep(new CallbackVariable<>(iterationCount -> 0.3 + Math.sin(iterationCount) / 4));
        animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / 20));
        animation.setShowFrequency(new Constant<>(0));
        animation.setTicksDuration(60);
    }

    @Override
    protected Wave initAnimation() {
        return new Wave();
    }


    /********* Circle specific setters ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, CircleBuilder.DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, CircleBuilder.DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setU(u);
        animation.setV(v);
    }

    public void setRadiusStart(double radiusStart) {
        if (radiusStart <= 0) {
            throw new IllegalArgumentException("RadiusStart should be positive.");
        }
        animation.setRadiusStart(radiusStart);
    }

    public void setRadiusStep(IVariable<Double> radiusStep) {
        animation.setRadiusStep(radiusStep);
    }

    public void setRadiusMax(double radiusMax) {
        if (radiusMax <= 0) {
            throw new IllegalArgumentException("RadiusMax should be positive.");
        }
        animation.setRadiusMax(radiusMax);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint) {
        setAngleBetweenEachPoint(angleBetweenEachPoint, false);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint, boolean fullCircle) {
        checkPositiveAndNotNull(angleBetweenEachPoint, "angleBetweenEachPoint should be positive", false);
        animation.setAngleBetweenEachPoint(angleBetweenEachPoint);
        if (fullCircle) {
            if (!angleBetweenEachPoint.isConstant())
                throw new IllegalArgumentException(CircleBuilder.FULL_CIRCLE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE);
            animation.setNbPoints(new Constant<>((int) Math.round(2 * Math.PI / angleBetweenEachPoint.getCurrentValue(0))));
        }
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        setNbPoints(nbPoints, false);
    }

    public void setNbPoints(IVariable<Integer> nbPoints, boolean fullCircle) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        if (fullCircle) {
            if (!nbPoints.isConstant())
                throw new IllegalArgumentException(CircleBuilder.FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE);
            animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / nbPoints.getCurrentValue(0)));
        }
    }

    @Override
    public Wave getAnimation() {
        checkNotNull(animation.getU(), CircleBuilder.DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getV(), CircleBuilder.DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
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
}
