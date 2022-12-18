package fr.skytale.particleanimlib.animation.animation.cuboid;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.*;

public class CuboidTask extends AAnimationTask<Cuboid> {

    private Vector fromLocationToFirstCorner;
    private Vector fromLocationToSecondCorner;
    private Double distanceBetweenPoints;

    public CuboidTask(Cuboid cuboid) {
        super(cuboid);
        startTask();
    }

    @Override
    protected boolean hasAnimationPointsChanged() {
        IVariable.ChangeResult<Vector> fromLocationToFirstCornerChangeResult = animation.getFromLocationToFirstCorner().willChange(iterationCount, fromLocationToFirstCorner);
        fromLocationToFirstCorner = fromLocationToFirstCornerChangeResult.getNewValue();
        IVariable.ChangeResult<Vector> fromLocationToSecondCornerChangeResult = animation.getFromLocationToSecondCorner().willChange(iterationCount, fromLocationToSecondCorner);
        fromLocationToSecondCorner = fromLocationToSecondCornerChangeResult.getNewValue();
        IVariable.ChangeResult<Double> distanceBetweenPointsChangeResult = animation.getDistanceBetweenPoints().willChange(iterationCount, distanceBetweenPoints);
        distanceBetweenPoints = distanceBetweenPointsChangeResult.getNewValue();

        return fromLocationToFirstCornerChangeResult.hasChanged() || fromLocationToSecondCornerChangeResult.hasChanged() || distanceBetweenPointsChangeResult.hasChanged();
    }

    @Override
    protected List<Vector> computeAnimationPoints() {
        Map<CuboidCorner, Vector> corners = getCorners();
        List<Vector> points = new ArrayList<>();

        //Collecting each edge points
        CuboidEdge.getEdges().forEach(edge -> {
            Vector firstCorner = corners.get(edge.firstVertice);
            Vector secondCorner = corners.get(edge.secondVertice);
            points.addAll(getLinePoints(firstCorner, secondCorner, distanceBetweenPoints));

        });
        return points;
    }

    private Map<CuboidCorner, Vector> getCorners() {
        Map<CuboidCorner, Vector> corners = new HashMap<>();

        double x1 = fromLocationToFirstCorner.getX();
        double y1 = fromLocationToFirstCorner.getY();
        double z1 = fromLocationToFirstCorner.getZ();
        double x2 = fromLocationToSecondCorner.getX();
        double y2 = fromLocationToSecondCorner.getY();
        double z2 = fromLocationToSecondCorner.getZ();

        double xMin, yMin, zMin, xMax, yMax, zMax;

        xMax = Math.max(x1, x2);
        xMin = Math.min(x1, x2);

        yMax = Math.max(y1, y2);
        yMin = Math.min(y1, y2);

        zMax = Math.max(z1, z2);
        zMin = Math.min(z1, z2);

        corners.put(CuboidCorner.UPPER_EAST_SOUTH, new Vector(xMax, yMax, zMax));
        corners.put(CuboidCorner.UPPER_EAST_NORTH, new Vector(xMax, yMax, zMin));
        corners.put(CuboidCorner.UPPER_WEST_SOUTH, new Vector(xMin, yMax, zMax));
        corners.put(CuboidCorner.UPPER_WEST_NORTH, new Vector(xMin, yMax, zMin));

        corners.put(CuboidCorner.LOWER_EAST_SOUTH, new Vector(xMax, yMin, zMax));
        corners.put(CuboidCorner.LOWER_EAST_NORTH, new Vector(xMax, yMin, zMin));
        corners.put(CuboidCorner.LOWER_WEST_SOUTH, new Vector(xMin, yMin, zMax));
        corners.put(CuboidCorner.LOWER_WEST_NORTH, new Vector(xMin, yMin, zMin));

        return corners;
    }

    public enum CuboidCorner {
        UPPER_EAST_SOUTH,
        UPPER_EAST_NORTH,
        UPPER_WEST_SOUTH,
        UPPER_WEST_NORTH,
        LOWER_EAST_SOUTH,
        LOWER_EAST_NORTH,
        LOWER_WEST_SOUTH,
        LOWER_WEST_NORTH;
    }

    private static class CuboidEdge {
        private static HashSet<CuboidEdge> edges;
        public final CuboidCorner firstVertice;
        public final CuboidCorner secondVertice;

        private CuboidEdge(CuboidCorner firstVertice, CuboidCorner secondVertice) {
            this.firstVertice = firstVertice;
            this.secondVertice = secondVertice;
        }

        public static HashSet<CuboidEdge> getEdges() {
            if (edges == null) {
                edges = initEdges();
            }
            return edges;
        }

        private static HashSet<CuboidEdge> initEdges() {
            HashSet<CuboidEdge> edgeSet = new HashSet<>();

            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_SOUTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_SOUTH, CuboidCorner.LOWER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_SOUTH, CuboidCorner.UPPER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.LOWER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_NORTH, CuboidCorner.UPPER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.LOWER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_NORTH, CuboidCorner.UPPER_WEST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.LOWER_WEST_NORTH));

            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_SOUTH, CuboidCorner.UPPER_WEST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_NORTH, CuboidCorner.LOWER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_NORTH, CuboidCorner.LOWER_WEST_NORTH));

            return edgeSet;
        }
    }

}
