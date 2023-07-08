package fr.skytale.particleanimlib.animation.animation.cuboid;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class CuboidTask extends AAnimationTask<Cuboid> {

    @IVariableCurrentValue(animationIVariableFieldName = "fromLocationToFirstCorner")
    private Vector fromCenterToFirstCorner;

    @IVariableCurrentValue(animationIVariableFieldName = "fromLocationToSecondCorner")
    private Vector fromCenterToOppositeCorner;

    @IVariableCurrentValue
    private Double distanceBetweenPoints;

    private Map<CuboidCorner, Vector> corners;

    public CuboidTask(Cuboid cuboid) {
        super(cuboid);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        corners = getCorners();
        List<AnimationPointData> points = new ArrayList<>();

        //Collecting each edge points
        CuboidEdge.getEdges().forEach(edge -> {
            Vector firstCorner = corners.get(edge.firstVertice);
            Vector secondCorner = corners.get(edge.secondVertice);
            points.addAll(AnimationTaskUtils.getLinePoints(firstCorner, secondCorner, distanceBetweenPoints));

        });
        return points;
    }

    private Map<CuboidCorner, Vector> getCorners() {

        Map<CuboidCorner, Vector> corners = new EnumMap<>(CuboidCorner.class);

        double x1 = fromCenterToFirstCorner.getX();
        double y1 = fromCenterToFirstCorner.getY();
        double z1 = fromCenterToFirstCorner.getZ();
        double x2 = fromCenterToOppositeCorner.getX();
        double y2 = fromCenterToOppositeCorner.getY();
        double z2 = fromCenterToOppositeCorner.getZ();

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

    // TODO probablement à supprimer car utilisé uniquement dans le contexte d'un mauvais calcul de collision
    public Map<CuboidCorner, Location> getCurrentCornersLocation() {
        return CuboidCorner.OPPOSITE_CORNERS.stream()
                .collect(Collectors.toMap(
                        cuboidCorner -> cuboidCorner,
                        cuboidCorner -> {
                            Vector fromCenterToCorner = corners.get(cuboidCorner);
                            Vector fromCenterToCornerRotated = currentRotation.rotateVector(fromCenterToCorner);
                            return getCurrentIterationBaseLocation().clone().add(fromCenterToCornerRotated);
                        }
                ));
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

        public static List<CuboidCorner> OPPOSITE_CORNERS = new ArrayList<>(Arrays.asList(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.UPPER_EAST_SOUTH));
    }

    private static class CuboidEdge {
        private static Set<CuboidEdge> edges;
        public final CuboidCorner firstVertice;
        public final CuboidCorner secondVertice;

        private CuboidEdge(CuboidCorner firstVertice, CuboidCorner secondVertice) {
            this.firstVertice = firstVertice;
            this.secondVertice = secondVertice;
        }

        public static Set<CuboidEdge> getEdges() {
            if (edges == null) {
                edges = initEdges();
            }
            return edges;
        }

        private static Set<CuboidEdge> initEdges() {
            HashSet<CuboidEdge> edgeSet = new HashSet<>();

            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_SOUTH, CuboidCorner.LOWER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.LOWER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.LOWER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.LOWER_WEST_SOUTH));

            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_SOUTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_SOUTH, CuboidCorner.UPPER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_EAST_NORTH, CuboidCorner.UPPER_WEST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.UPPER_WEST_NORTH, CuboidCorner.UPPER_WEST_SOUTH));

            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_SOUTH, CuboidCorner.UPPER_WEST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_NORTH, CuboidCorner.UPPER_EAST_NORTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_EAST_SOUTH, CuboidCorner.UPPER_EAST_SOUTH));
            edgeSet.add(new CuboidEdge(CuboidCorner.LOWER_WEST_NORTH, CuboidCorner.UPPER_WEST_NORTH));

            return edgeSet;
        }
    }

}
