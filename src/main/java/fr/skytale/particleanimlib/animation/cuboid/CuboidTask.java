package fr.skytale.particleanimlib.animation.cuboid;

import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.parent.ARotatingAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CuboidTask extends ARotatingAnimationTask<Cuboid> {

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
        public final CuboidCorner firstVertice;
        public final CuboidCorner secondVertice;

        private CuboidEdge(CuboidCorner firstVertice, CuboidCorner secondVertice) {
            this.firstVertice = firstVertice;
            this.secondVertice = secondVertice;
        }

        private static HashSet<CuboidEdge> edges;

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

    private final double step;

    public Map<CuboidCorner, Vector> corners;

    public CuboidTask(Cuboid cuboid) {
        super(cuboid);
        this.step = animation.getStep();
        this.corners = getCorners();

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void showRotated(Location iterationBaseLocation) {
        //get locations
        Map<CuboidCorner, Location> cornersLocation = this.corners.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> iterationBaseLocation.clone().add(e.getValue())));

        Set<CuboidEdge> edges = CuboidEdge.getEdges();

        edges.forEach(edge -> {
            Location firstCorner = cornersLocation.get(edge.firstVertice);
            Location secondCorner = cornersLocation.get(edge.secondVertice);
            drawLine(firstCorner, secondCorner, step);
        });
    }

    @Override
    protected void computeRotation(Vector currentAxis, double currentStepAngleAlpha) {
        this.corners = this.corners.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new CustomVector(e.getValue()).rotateAroundAxis(currentAxis, currentStepAngleAlpha)));
    }


    private Map<CuboidCorner, Vector> getCorners() {
        Map<CuboidCorner, Vector> corners = new HashMap<>();

        double x1 = animation.getFromLocationToFirstCorner().getX();
        double y1 = animation.getFromLocationToFirstCorner().getY();
        double z1 = animation.getFromLocationToFirstCorner().getZ();
        double x2 = animation.getFromLocationToSecondCorner().getX();
        double y2 = animation.getFromLocationToSecondCorner().getY();
        double z2 = animation.getFromLocationToSecondCorner().getZ();

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

}
