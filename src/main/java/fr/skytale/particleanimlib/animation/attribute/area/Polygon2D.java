package fr.skytale.particleanimlib.animation.attribute.area;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a polygon area
 */
public class Polygon2D implements IArea {
    private final int nbVertices;
    private final int radius;
    private final List<Vector2D> points;

    /**
     * Builds a polygon area
     * @param nbVertices the number of vertices of the polygon
     * @param radius the radius of the polygon
     */
    public Polygon2D(int nbVertices, int radius) {
        if (nbVertices < 3) throw new IllegalArgumentException("A polygon must have at least 3 vertices");
        this.nbVertices = nbVertices;
        this.radius = radius;
        this.points = computeVertexes();
    }

    private List<Vector2D> computeVertexes() {
        double stepAngle = 2 * Math.PI / nbVertices;
        List<Vector2D> vertices = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < nbVertices; pointIndex++) {
            double theta = pointIndex * stepAngle;
            vertices.add(new Vector2D(radius * Math.cos(theta), radius * Math.sin(theta)));
        }
        return vertices;
    }
    /**
     * Return true if the given point is contained inside the boundary.
     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     * @param pointVector The point to check
     * @return true if the point is inside the boundary, false otherwise
     *
     */
    @Override
    public boolean isInside(Vector pointVector) {

        Vector2D pointVector2D = new Vector2D(pointVector.getX(), pointVector.getZ());

        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if (
                    (points.get(i).getY() > pointVector2D.getY()) != (points.get(j).getY() > pointVector2D.getY()) &&
                    (
                            pointVector2D.getX() <
                            (points.get(j).getX() - points.get(i).getX()) * (pointVector2D.getY() - points.get(i).getY()) /
                            (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX()
                    )) {
                result = !result;
            }
        }
        return result;
    }
}
