package fr.skytale.particleanimlib.animation.attribute.area;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a polygon area
 */
public class Prism implements IArea {
    private final int nbBaseVertices;
    private final int baseRadius;
    private final List<Vector2D> points;

    /**
     * Builds a polygon area
     * @param nbBaseVertices the number of vertices of the polygon that correspond to the prism bottom face
     * @param baseRadius the radius of the circle in which the prism bottom face is inscribed
     */
    public Prism(int nbBaseVertices, int baseRadius) {
        if (nbBaseVertices < 3) throw new IllegalArgumentException("A polygon must have at least 3 vertices");
        this.nbBaseVertices = nbBaseVertices;
        this.baseRadius = baseRadius;
        this.points = computeVertexes();
    }

    private List<Vector2D> computeVertexes() {
        double stepAngle = 2 * Math.PI / nbBaseVertices;
        List<Vector2D> vertices = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < nbBaseVertices; pointIndex++) {
            double theta = pointIndex * stepAngle;
            vertices.add(new Vector2D(baseRadius * Math.cos(theta), baseRadius * Math.sin(theta)));
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
