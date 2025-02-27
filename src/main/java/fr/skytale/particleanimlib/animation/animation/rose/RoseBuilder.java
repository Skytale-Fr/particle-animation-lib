package fr.skytale.particleanimlib.animation.animation.rose;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

public class RoseBuilder extends AAnimationBuilder<Rose, RoseTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    private static final String ROSE_MODIFIER_DENOMINATOR_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "roseModifierDenominator must not be NULL or equal to 0.";
    private static final String ROSE_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL = "roseModifierNumerator must not be equal to 1 because it would have draw a simple circle.";

    public RoseBuilder() {
        super();
        animation.setRadius(3.0);
        animation.setNbPoints(20);
        animation.setRoseModifierNumerator(3d);
        animation.setRoseModifierDenominator(2);
        animation.setShowPeriod(1);
        animation.setTicksDuration(60);
    }

    @Override
    protected Rose initAnimation() {
        return new Rose();
    }

    @Override
    public Rose getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkNotNullOrZero(animation.getRoseModifierDenominator(), ROSE_MODIFIER_DENOMINATOR_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNull(animation.getRoseModifierNumerator(), ROSE_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL);
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }

    /********* Rose specific setters ***********/

    /**
     * Defines the radius of the rose
     * @param radius the radius
     */
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    /**
     * Defines the radius of the rose
     * @param radius the radius
     */
    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    /**
     * Defines the number of points of the rose
     * @param nbPoints the number of points
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    /**
     * Defines the number of points of the rose
     * @param nbPoints the number of points
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    /**
     * Defines the numerator of the rose modifier
     * @see <a href="https://mathcurve.com/courbes2d/rosace/rosace.shtml">rose on mathcurve.com</a>
     * @param roseModifierNumerator the numerator of the rose modifier
     */
    public void setRoseModifierNumerator(IVariable<Double> roseModifierNumerator) {
        animation.setRoseModifierNumerator(roseModifierNumerator);
    }

    /**
     * Defines the numerator of the rose modifier
     * @see <a href="https://mathcurve.com/courbes2d/rosace/rosace.shtml">rose on mathcurve.com</a>
     * @param roseModifierNumerator the numerator of the rose modifier
     */
    public void setRoseModifierNumerator(double roseModifierNumerator) {
        setRoseModifierNumerator(new Constant<>(roseModifierNumerator));
    }

    /**
     * Defines the denominator of the rose modifier
     * @see <a href="https://mathcurve.com/courbes2d/rosace/rosace.shtml">rose on mathcurve.com</a>
     * @param roseModifierDenominator the denominator of the rose modifier
     */
    public void setRoseModifierDenominator(IVariable<Integer> roseModifierDenominator) {
        animation.setRoseModifierDenominator(roseModifierDenominator);
    }

    /**
     * Defines the denominator of the rose modifier
     * @see <a href="https://mathcurve.com/courbes2d/rosace/rosace.shtml">rose on mathcurve.com</a>
     * @param roseModifierDenominator the denominator of the rose modifier
     */
    public void setRoseModifierDenominator(int roseModifierDenominator) {
        setRoseModifierDenominator(new Constant<>(roseModifierDenominator));
    }
}
