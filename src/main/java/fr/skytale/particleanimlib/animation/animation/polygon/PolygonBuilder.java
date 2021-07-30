package fr.skytale.particleanimlib.animation.animation.polygon;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.util.Vector;

public class PolygonBuilder extends ARotatingAnimationBuilder<Polygon> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";

    public PolygonBuilder() {
        super();
        animation.setU(new Vector(1, 0, 0));
        animation.setV(new Vector(0, 1, 0));
        animation.setDistanceFromCenterToVertices(new Constant<>(4.0));
        animation.setDistanceBetweenPoints(new Constant<>(0.3));
        animation.setNbVertices(new Constant<>(8));
        animation.setShowPeriod(new Constant<>(1));
        animation.setTicksDuration(60);
    }

    @Override
    protected Polygon initAnimation() {
        return new Polygon();
    }

    /********* Circle specific setters ***********/
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

    public void setNbVertices(IVariable<Integer> nbVertices) {
        animation.setNbVertices(nbVertices);
        checkNotNull(nbVertices, "nbVertices should not be null");
        checkSuperior(nbVertices, new Constant<>(3), "nbVertices should be at least 3", true);
    }

    public void setNbVertices(int nbVertices) {
        setNbVertices(new Constant<>(nbVertices));
    }

    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }

    public void setDistanceFromCenterToVertices(IVariable<Double> distanceFromCenterToVertices) {
        checkNotNull(distanceFromCenterToVertices, "distanceFromCenterToVertices must not be null");
        animation.setDistanceFromCenterToVertices(distanceFromCenterToVertices);
    }

    public void setDistanceFromCenterToVertices(double distanceFromCenterToVertices) {
        setDistanceFromCenterToVertices(new Constant<>(distanceFromCenterToVertices));
    }

    @Override
    public Polygon getAnimation() {
        checkNotNull(animation.getU(), DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getV(), DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getNbVertices(), "nbVertices should not be null");
        checkSuperior(animation.getNbVertices(), new Constant<>(3), "nbVertices should be at least 3", true);
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        checkNotNull(animation.getDistanceFromCenterToVertices(), "distanceFromCenterToVertices must not be null");
        return super.getAnimation();
    }
}
