package fr.skytale.particleanimlib.animation.animation.epi;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class EpiBuilder extends AAnimationBuilder<Epi, EpiTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_EPI_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE =
            "To do a fullEpi, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full epi) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_EPI_NB_POINTS_ERROR_MESSAGE =
            "To do a fullEpi, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full epi) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    private static final String EPI_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "epiModifier must not be NULL or equal to 0.";

    public EpiBuilder() {
        super();
        animation.setRadius(new Constant<>(3.0));
        animation.setEpiModifierNumerator(new Constant<>(3d));
        animation.setEpiModifierDenominator(new Constant<>(2));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20));
    }

    @Override
    protected Epi initAnimation() {
        return new Epi();
    }

    @Override
    public Epi getAnimation() {
        checkPositiveAndNotNull(animation.getRadius(), "radius should be positive.", false);
        checkNotNullOrZero(animation.getEpiModifierDenominator(), EPI_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNull(animation.getEpiModifierNumerator(), "EPI_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL");
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }

    /********* Epi specific setters ***********/

    /**
     * Defines the radius of the epi
     * @param radius the radius of the epi
     */
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    /**
     * Defines the radius of the epi
     * @param radius the radius of the epi
     */
    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
    }

    /**
     * Defines the maximum radius in which the points will be displayed
     * @param maxRadius the maximal distance between the center and the displayed points
     */
    public void setMaxRadius(IVariable<Double> maxRadius) {
        checkPositive(maxRadius, "maxRadius should be positive.", false);
        animation.setMaxRadius(maxRadius);
    }

    /**
     * Defines the maximum radius in which the points will be displayed
     * @param maxRadius the maximal distance between the center and the displayed points
     */
    public void setMaxRadius(double maxRadius) {
        setMaxRadius(new Constant<>(maxRadius));
    }

    /**
     * Defines the number of points of the epi
     * @param nbPoints the number of points of the epi
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    /**
     * Defines the number of points of the epi
     * @param nbPoints the number of points of the epi
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    /**
     * Defines the numerator of the epi modifier.
     * @see <a href="https://mathcurve.com/courbes2d/epi/epi.shtml">Epi on mathcurve.com</a>
     * @param epiModifierNumerator the numerator of the epi modifier
     */
    public void setEpiModifierNumerator(IVariable<Double> epiModifierNumerator) {
        animation.setEpiModifierNumerator(epiModifierNumerator);
    }

    /**
     * Defines the numerator of the epi modifier.
     * @see <a href="https://mathcurve.com/courbes2d/epi/epi.shtml">Epi on mathcurve.com</a>
     * @param epiModifierNumerator the numerator of the epi modifier
     */
    public void setEpiModifierNumerator(double epiModifierNumerator) {
        setEpiModifierNumerator(new Constant<>(epiModifierNumerator));
    }

    /**
     * Defines the denominator of the epi modifier.
     * @see <a href="https://mathcurve.com/courbes2d/epi/epi.shtml">Epi on mathcurve.com</a>
     * @param epiModifierDenominator the denominator of the epi modifier
     */
    public void setEpiModifierDenominator(IVariable<Integer> epiModifierDenominator) {
        animation.setEpiModifierDenominator(epiModifierDenominator);
    }


    /**
     * Defines the denominator of the epi modifier.
     * @see <a href="https://mathcurve.com/courbes2d/epi/epi.shtml">Epi on mathcurve.com</a>
     * @param epiModifierDenominator the denominator of the epi modifier
     */
    public void setEpiModifierDenominator(int epiModifierDenominator) {
        setEpiModifierDenominator(new Constant<>(epiModifierDenominator));
    }
}
