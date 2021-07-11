package fr.skytale.particleanimlib.animation.animation.cuboid;

import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class CuboidTask extends ARotatingAnimationTask<Cuboid> {

    public Map<CuboidCorner, Vector> corners;
    public Map<CuboidCorner, Vector> rotatedCorners;

    public CuboidTask(Cuboid cuboid) {
        super(cuboid);
        startTask();
    }

    @Override
    public void showRotated(boolean rotationChanged, Location iterationBaseLocation) {

        boolean cornersUpdated = false;
        //recalculate corners if required
        if (corners == null || animation.getFromLocationToFirstCorner().willChange(iterationCount) || animation.getFromLocationToSecondCorner().willChange(iterationCount)) {
            this.corners = getCorners();
            cornersUpdated = true;
        }

        //rotate each corners according to the rotations accumulated since the first iteration (which are saved in this.rotation) if required
        if (hasRotation) {
            if (rotatedCorners == null || cornersUpdated || rotationChanged) {
                this.rotatedCorners = this.corners.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> this.rotation.rotateVector(e.getValue())
                        ));
            }
        } else {
            this.rotatedCorners = corners;
        }

        //get locations
        final Map<CuboidCorner, Location> cornersLocation = this.rotatedCorners.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> iterationBaseLocation.clone().add(e.getValue())));

        double distanceBetweenPoints = animation.getDistanceBetweenPoints().getCurrentValue(iterationCount);

        //Drawing each edge
        CuboidEdge.getEdges().forEach(edge -> {
            Location firstCorner = cornersLocation.get(edge.firstVertice);
            Location secondCorner = cornersLocation.get(edge.secondVertice);
            drawLine(firstCorner, secondCorner, distanceBetweenPoints);
        });
    }

    private Map<CuboidCorner, Vector> getCorners() {
        Map<CuboidCorner, Vector> corners = new HashMap<>();

        Vector firstCorner = animation.getFromLocationToFirstCorner().getCurrentValue(iterationCount);
        Vector secondCorner = animation.getFromLocationToSecondCorner().getCurrentValue(iterationCount);

        double x1 = firstCorner.getX();
        double y1 = firstCorner.getY();
        double z1 = firstCorner.getZ();
        double x2 = secondCorner.getX();
        double y2 = secondCorner.getY();
        double z2 = secondCorner.getZ();

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

    private enum CuboidCorner {
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

            //UPPER EDGES
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_NORTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_NORTH, CuboidCorner.UPPER_WEST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_NORTH, CuboidCorner.UPPER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_SOUTH, CuboidCorner.UPPER_WEST_SOUTH));

            //LOWER EDGES
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.LOWER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.LOWER_WEST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.LOWER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.LOWER_WEST_SOUTH));

            //SIDE EDGES
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.UPPER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.UPPER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_SOUTH, CuboidCorner.UPPER_WEST_SOUTH));

            return edgeSet;
        }
    }

}
