package fr.skytale.particleanimlib.animation.animation.torussolenoid;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
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
        animation.setRotation(new Vector(1, 0, 0), new Vector(0, 1, 0));
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

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
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
    public void setTorusRadius(IVariable<Double> torusRadius) {
        checkPositiveAndNotNull(torusRadius, "torusRadius should be positive.", false);
        animation.setTorusRadius(torusRadius);
    }

    public void setTorusRadius(double torusRadius) {
        setTorusRadius(new Constant<>(torusRadius));
    }

    public void setSolenoidRadius(IVariable<Double> solenoidRadius) {
        checkPositiveAndNotNull(solenoidRadius, "solenoidRadius should be positive.", false);
        animation.setSolenoidRadius(solenoidRadius);
    }

    public void setSolenoidRadius(double solenoidRadius) {
        setSolenoidRadius(new Constant<>(solenoidRadius));
    }

    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setRotation(u, v);
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setDirectorVectors(plane.u, plane.v);
    }

    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
    }

    public void setTorusSolenoidModifierNumerator(IVariable<Double> torusSolenoidModifierNumerator) {
        animation.setTorusSolenoidModifierNumerator(torusSolenoidModifierNumerator);
    }

    public void setTorusSolenoidModifierNumerator(double torusSolenoidModifierNumerator) {
        setTorusSolenoidModifierNumerator(new Constant<>(torusSolenoidModifierNumerator));
    }

    public void setTorusSolenoidModifierDenominator(IVariable<Integer> torusSolenoidModifierDenominator) {
        animation.setTorusSolenoidModifierDenominator(torusSolenoidModifierDenominator);
    }

    public void setTorusSolenoidModifierDenominator(int torusSolenoidModifierDenominator) {
        setTorusSolenoidModifierDenominator(new Constant<>(torusSolenoidModifierDenominator));
    }
}
