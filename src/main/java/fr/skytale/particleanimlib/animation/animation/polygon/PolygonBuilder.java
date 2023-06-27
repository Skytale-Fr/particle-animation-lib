package fr.skytale.particleanimlib.animation.animation.polygon;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class PolygonBuilder extends AAnimationBuilder<Polygon, PolygonTask> {

    public static final String DIRECTOR_VECTOR_U_SHOULD_NOT_BE_NULL = "directorVector u should not be null";
    public static final String DIRECTOR_VECTOR_V_SHOULD_NOT_BE_NULL = "directorVector v should not be null";

    public PolygonBuilder() {
        super();
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

    @Override
    public Polygon getAnimation() {
        checkNotNull(animation.getNbVertices(), "nbVertices should not be null");
        checkSuperior(animation.getNbVertices(), new Constant<>(3), "nbVertices should be at least 3", true);
        checkNotNull(animation.getDistanceBetweenPoints(), "distanceBetweenPoints must not be null");
        checkNotNull(animation.getDistanceFromCenterToVertices(), "distanceFromCenterToVertices must not be null");
        return super.getAnimation();
    }

    /********* Circle specific setters ***********/

    /**
     * Set the number of vertices of the polygon
     * @param nbVertices the number of vertices
     */
    public void setNbVertices(IVariable<Integer> nbVertices) {
        animation.setNbVertices(nbVertices);
        checkNotNull(nbVertices, "nbVertices should not be null");
        checkSuperior(nbVertices, new Constant<>(3), "nbVertices should be at least 3", true);
    }

    /**
     * Set the number of vertices of the polygon
     * @param nbVertices the number of vertices
     */
    public void setNbVertices(int nbVertices) {
        setNbVertices(new Constant<>(nbVertices));
    }

    /**
     * Set the distance between each point of the polygon in the polygon edges
     * @param distanceBetweenPoints the distance between each point
     */
    public void setDistanceBetweenPoints(IVariable<Double> distanceBetweenPoints) {
        checkNotNull(distanceBetweenPoints, "distanceBetweenPoints must not be null");
        animation.setDistanceBetweenPoints(distanceBetweenPoints);
    }

    /**
     * Set the distance between each point of the polygon in the polygon edges
     * @param distanceBetweenPoints the distance between each point
     */
    public void setDistanceBetweenPoints(double distanceBetweenPoints) {
        setDistanceBetweenPoints(new Constant<>(distanceBetweenPoints));
    }

    /**
     * Set the distance between the animation position and the vertices of the polygon
     * @param distanceFromCenterToVertices the distance between the animation position and the vertices of the polygon
     */
    public void setDistanceFromCenterToVertices(IVariable<Double> distanceFromCenterToVertices) {
        checkNotNull(distanceFromCenterToVertices, "distanceFromCenterToVertices must not be null");
        animation.setDistanceFromCenterToVertices(distanceFromCenterToVertices);
    }

    /**
     * Set the distance between the animation position and the vertices of the polygon
     * @param distanceFromCenterToVertices the distance between the animation position and the vertices of the polygon
     */
    public void setDistanceFromCenterToVertices(double distanceFromCenterToVertices) {
        setDistanceFromCenterToVertices(new Constant<>(distanceFromCenterToVertices));
    }
}
