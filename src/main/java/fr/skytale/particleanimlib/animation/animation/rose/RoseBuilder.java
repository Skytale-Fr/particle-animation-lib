package fr.skytale.particleanimlib.animation.animation.rose;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class RoseBuilder extends AAnimationBuilder<Rose, RoseTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    private static final String ROSE_MODIFIER_DENOMINATOR_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "roseModifierDenominator must not be NULL or equal to 0.";
    private static final String ROSE_MODIFIER_NUMERATOR_MUST_NOT_BE_NULL = "roseModifierNumerator must not be equal to 1 because it would have draw a simple circle.";

    public RoseBuilder() {
        super();
        animation.setRotation(new Vector(1, 0, 0), new Vector(0, 1, 0));
        animation.setRadius(new Constant<>(3.0));
        animation.setRoseModifierNumerator(new Constant<>(3d));
        animation.setRoseModifierDenominator(new Constant<>(2));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20));
    }

    @Override
    protected Rose initAnimation() {
        return new Rose();
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
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
    public void setRadius(IVariable<Double> radius) {
        checkPositiveAndNotNull(radius, "radius should be positive.", false);
        animation.setRadius(radius);
    }

    public void setRadius(double radius) {
        setRadius(new Constant<>(radius));
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

    public void setRoseModifierNumerator(IVariable<Double> roseModifierNumerator) {
        animation.setRoseModifierNumerator(roseModifierNumerator);
    }

    public void setRoseModifierNumerator(double roseModifierNumerator) {
        setRoseModifierNumerator(new Constant<>(roseModifierNumerator));
    }

    public void setRoseModifierDenominator(IVariable<Integer> roseModifierDenominator) {
        animation.setRoseModifierDenominator(roseModifierDenominator);
    }

    public void setRoseModifierDenominator(int roseModifierDenominator) {
        setRoseModifierDenominator(new Constant<>(roseModifierDenominator));
    }
}
