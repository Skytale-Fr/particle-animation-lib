package fr.skytale.particleanimlib.animation.animation.rose;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingRoundAnimationBuilder;
import org.bukkit.util.Vector;

public class RoseBuilder extends ARotatingRoundAnimationBuilder<Rose> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_ROSE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE = "To do a fullRose, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full rose) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_ROSE_NB_POINTS_ERROR_MESSAGE = "To do a fullRose, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full rose) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    private static final String ROSE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0 = "roseModifier must not be NULL or equal to 0.";
    private static final String ROSE_MODIFIER_MUST_NOT_BE_EQUAL_TO_1 = "roseModifier must not be equal to 1 because it would have draw a simple circle.";

    public RoseBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setRadius(new Constant<>(3.0));
        animation.setRoseModifier(new Constant<>(3/2d));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20), true);
    }

    @Override
    protected Rose initAnimation() {
        return new Rose();
    }

    /********* Rose specific setters ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setU(u);
        animation.setV(v);
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        animation.setU(plane.u);
        animation.setV(plane.v);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint, boolean fullRose) {
        super.setAngleBetweenEachPoint(angleBetweenEachPoint);
        if (fullRose) {
            if (!angleBetweenEachPoint.isConstant())
                throw new IllegalArgumentException(FULL_ROSE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE);
            animation.setNbPoints(new Constant<>((int) Math.round(2 * Math.PI / angleBetweenEachPoint.getCurrentValue(0))));
        }
    }

    public void setAngleBetweenEachPoint(double angleBetweenEachPoint, boolean fullRose) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint), fullRose);
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        setNbPoints(nbPoints, false);
    }

    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    public void setNbPoints(IVariable<Integer> nbPoints, boolean fullRose) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        if (fullRose) {
            if (!nbPoints.isConstant()) throw new IllegalArgumentException(FULL_ROSE_NB_POINTS_ERROR_MESSAGE);
            animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / nbPoints.getCurrentValue(0)));
        }
    }

    public void setNbPoints(int nbPoints, boolean fullRose) {
        setNbPoints(new Constant<>(nbPoints), fullRose);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    public void setRoseModifier(IVariable<Double> roseModifier){
        animation.setRoseModifier(roseModifier);
    }

    public void setRoseModifier(double roseModifier){
        setRoseModifier(new Constant<>(roseModifier));
    }

    @Override
    public Rose getAnimation() {
        checkNotNull(animation.getU(), DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getV(), DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        checkNotNullOrZero(animation.getRoseModifier(), ROSE_MODIFIER_MUST_NOT_BE_NULL_OR_EQUAL_TO_0);
        checkNotNullOrEquals(animation.getRoseModifier(), 1, ROSE_MODIFIER_MUST_NOT_BE_EQUAL_TO_1);
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }
}
