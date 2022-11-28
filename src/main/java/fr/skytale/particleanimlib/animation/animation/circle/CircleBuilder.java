package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARoundAnimationBuilder;
import org.bukkit.util.Vector;

public class CircleBuilder extends ARoundAnimationBuilder<Circle, CircleTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";
    public static final String FULL_CIRCLE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE = "To do a fullCircle, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute nbPoints value, angleBetweenEachPoint have to be a constant.\n" +
            "Else you will have to compute the nbPoints evolving value yourself.\n" +
            "Reminder (for a full circle) : nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";
    public static final String FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE = "To do a fullCircle, nbPoints and angleBetweenEachPoint should have related values.\n" +
            "In order to automatically compute angleBetweenEachPoint value, nbPoints have to be a constant.\n" +
            "Else you will have to compute the angleBetweenEachPoint evolving value yourself.\n" +
            "Reminder (for a full circle) :\n" +
            "angleBetweenEachPoint = 2 * Math.PI / nbPoints" +
            "nbPoints = (int) Math.round(2 * Math.PI / angleBetweenEachPoint)";

    public CircleBuilder() {
        super();
        animation.setRotation(new Vector(1, 0, 0),new Vector(0, 1, 0));
        animation.setRadius(new Constant<>(3.0));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
        setNbPoints(new Constant<>(20), true);
    }

    @Override
    protected Circle initAnimation() {
        return new Circle();
    }

    /********* Circle specific setters ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        checkNotNull(u, DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(v, DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        animation.setRotation(u,v);
    }

    public void setDirectorVectorsFromOrientation(Orientation direction, double length) {
        setDirectorVectors(direction.getU(length), direction.getV(length));
    }

    public void setDirectorVectorsFromNormalVector(Vector normal) {
        RotatableVector.Plane2D plane = new RotatableVector(normal).getPlane();
        setDirectorVectors(plane.u,plane.v);
    }

    public void setAngleBetweenEachPoint(IVariable<Double> angleBetweenEachPoint, boolean fullCircle) {
        super.setAngleBetweenEachPoint(angleBetweenEachPoint);
        if (fullCircle) {
            if (!angleBetweenEachPoint.isConstant())
                throw new IllegalArgumentException(FULL_CIRCLE_ANGLE_BETWEEN_EACH_POINT_ERROR_MESSAGE);
            animation.setNbPoints(new Constant<>((int) Math.round(2 * Math.PI / angleBetweenEachPoint.getCurrentValue(0))));
        }
    }

    public void setAngleBetweenEachPoint(double angleBetweenEachPoint, boolean fullCircle) {
        setAngleBetweenEachPoint(new Constant<>(angleBetweenEachPoint), fullCircle);
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        setNbPoints(nbPoints, false);
    }

    public void setNbPoints(int nbPoints) {
        setNbPoints(new Constant<>(nbPoints));
    }

    public void setNbPoints(IVariable<Integer> nbPoints, boolean fullCircle) {
        animation.setNbPoints(nbPoints);
        checkPositiveAndNotNull(nbPoints, "nbPoints should be positive", false);
        if (fullCircle) {
            if (!nbPoints.isConstant()) throw new IllegalArgumentException(FULL_CIRCLE_NB_POINTS_ERROR_MESSAGE);
            animation.setAngleBetweenEachPoint(new Constant<>(2 * Math.PI / nbPoints.getCurrentValue(0)));
        }
    }

    public void setNbPoints(int nbPoints, boolean fullCircle) {
        setNbPoints(new Constant<>(nbPoints), fullCircle);
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    @Override
    public Circle getAnimation() {
        checkPositiveAndNotNull(animation.getNbPoints(), "nbPoints should be positive", false);
        return super.getAnimation();
    }
}
