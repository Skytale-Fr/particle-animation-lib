package fr.skytale.particleanimlib.animation.animation.torussolenoid;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class TorusSolenoidBuilder extends AAnimationBuilder<TorusSolenoid, TorusSolenoidTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    private static final String TORUS_SOLENOID_MODIFIER_DENOMINATOR_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "torusSolenoidModifierDenominator must not be NULL or equal to 0.";
    private static final String TORUS_SOLENOID_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL = "torusSolenoidModifierNumerator must not be equal to 1 because it would have draw a simple circle.";

    public TorusSolenoidBuilder() {
        super();
        animation.setSolenoidRadius(new Constant<>(3.0));
        animation.setTorusRadius(new Constant<>(3.0));
        animation.setTorusSolenoidModifierNumerator(new Constant<>(3d));
        animation.setTorusSolenoidModifierDenominator(new Constant<>(2));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20));
    }

    @Override
    protected TorusSolenoid initAnimation() {
        return new TorusSolenoid();
    }

    @Override
    public TorusSolenoid getAnimation() {
        checkNotNull(animation.getTorusRadius(), "torusRadius should not be null.");
        checkPositiveAndNotNull(animation.getSolenoidRadius(), "torusRadius should be positive.", false);
        checkNotNullOrZero(animation.getTorusSolenoidModifierDenominator(), TORUS_SOLENOID_MODIFIER_DENOMINATOR_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNull(animation.getTorusSolenoidModifierNumerator(), TORUS_SOLENOID_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL);
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }

    /********* TorusSolenoid specific setters ***********/

    /**
     * Set the torus radius
     * @param torusRadius the torus radius
     */
    public void setTorusRadius(IVariable<Double> torusRadius) {
        checkPositiveAndNotNull(torusRadius, "torusRadius should be positive.", false);
        animation.setTorusRadius(torusRadius);
    }

    /**
     * Set the torus radius
     * @param torusRadius the torus radius
     */
    public void setTorusRadius(double torusRadius) {
        setTorusRadius(new Constant<>(torusRadius));
    }

    /**
     * Set the radius of the solenoid
     * @param solenoidRadius the radius of the solenoid
     */
    public void setSolenoidRadius(IVariable<Double> solenoidRadius) {
        checkPositiveAndNotNull(solenoidRadius, "solenoidRadius should be positive.", false);
        animation.setSolenoidRadius(solenoidRadius);
    }

    /**
     * Set the radius of the solenoid
     * @param solenoidRadius the radius of the solenoid
     */
    public void setSolenoidRadius(double solenoidRadius) {
        setSolenoidRadius(new Constant<>(solenoidRadius));
    }

    /**
     * Set the number of points of the torus solenoid
     * @param nbPoints the number of points of the torus solenoid
     */
    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    /**
     * Set the number of points of the torus solenoid
     * @param nbPoints the number of points of the torus solenoid
     */
    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    /**
     * Set the numerator of the torus solenoid modifier
     * @see <a href="https://mathcurve.com/courbes3d/solenoidtoric/solenoidtoric.shtml">solenoid torus on mathcurve.com</a>
     * @param torusSolenoidModifierNumerator the numerator of the torus solenoid modifier
     */
    public void setTorusSolenoidModifierNumerator(IVariable<Double> torusSolenoidModifierNumerator) {
        animation.setTorusSolenoidModifierNumerator(torusSolenoidModifierNumerator);
    }

    /**
     * Set the numerator of the torus solenoid modifier
     * @see <a href="https://mathcurve.com/courbes3d/solenoidtoric/solenoidtoric.shtml">solenoid torus on mathcurve.com</a>
     * @param torusSolenoidModifierNumerator the numerator of the torus solenoid modifier
     */
    public void setTorusSolenoidModifierNumerator(double torusSolenoidModifierNumerator) {
        setTorusSolenoidModifierNumerator(new Constant<>(torusSolenoidModifierNumerator));
    }

    /**
     * Set the denominator of the torus solenoid modifier
     * @see <a href="https://mathcurve.com/courbes3d/solenoidtoric/solenoidtoric.shtml">solenoid torus on mathcurve.com</a>
     * @param torusSolenoidModifierDenominator the denominator of the torus solenoid modifier
     */
    public void setTorusSolenoidModifierDenominator(IVariable<Integer> torusSolenoidModifierDenominator) {
        animation.setTorusSolenoidModifierDenominator(torusSolenoidModifierDenominator);
    }

    /**
     * Set the denominator of the torus solenoid modifier
     * @see <a href="https://mathcurve.com/courbes3d/solenoidtoric/solenoidtoric.shtml">solenoid torus on mathcurve.com</a>
     * @param torusSolenoidModifierDenominator the denominator of the torus solenoid modifier
     */
    public void setTorusSolenoidModifierDenominator(int torusSolenoidModifierDenominator) {
        setTorusSolenoidModifierDenominator(new Constant<>(torusSolenoidModifierDenominator));
    }
}
