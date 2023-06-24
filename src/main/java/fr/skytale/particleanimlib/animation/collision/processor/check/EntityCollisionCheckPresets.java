package fr.skytale.particleanimlib.animation.collision.processor.check;

import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereTask;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.Point;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class EntityCollisionCheckPresets {


    private EntityCollisionCheckPresets() {
        //prevents instantiation
    }


    /**
     * This preset checks if the entity collides with a particle.<br />
     * This preset works with every type of animation. <br />
     * The verification is made with the bounding box of the entity and should be exact.
     */
    public static final EntityCollisionCheckPreset<? extends AAnimationTask<?>> EXACT_BOUNDING_BOX = new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
        BoundingBox entityBoundingBox = target.getBoundingBox();
        return entityBoundingBox.contains(location.getX(), location.getY(), location.getZ());
    });

    /**
     * This preset checks if the entity is inside a cuboid. <br />
     * This preset works with only the Cuboid animation. <br />
     * The verification is made with the bounding box of the entity and the one of the cuboid. So it should be exact.
     */
    public static final EntityCollisionCheckPreset<CuboidTask> EXACT_BOUNDING_BOX_INSIDE_CUBOID = new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
        Location min = animationTask.getCurrentCornersLocation().get(CuboidTask.CuboidCorner.LOWER_WEST_NORTH);
        Location max = animationTask.getCurrentCornersLocation().get(CuboidTask.CuboidCorner.UPPER_EAST_SOUTH);
        return BoundingBox.of(min, max).contains(target.getBoundingBox());
        //TODO ça ne fonctionne pas car on est sur une AxisAlignedBB alors que le cuboid peut avoir subit une rotation
    });

    /**
     * This preset checks if the entity is inside a sphere.<br />
     * This preset works with only the Sphere animation. <br />
     * The verification is made with the center of the entity's bounding box. This way, the check isn't exact.
     */
    public static final EntityCollisionCheckPreset<SphereTask> TARGET_CENTER_INSIDE_SPHERE = new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
        Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
        Vector targetCenter = target.getBoundingBox().getCenter();
        double radius = animationTask.getCurrentRadius();
        return targetCenter.isInSphere(sphereCenter, radius);
    });

    /**
     * This preset checks if the entity is inside a circle. <br />
     * This preset works with only the Circle animation. <br />
     * The verification is made with the center of the entity's bounding box and the 2d plan of the circle. This way, the check isn't exact.
     */
    public static final EntityCollisionCheckPreset<CircleTask> TARGET_CENTER_INSIDE_CIRCLE = new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
        Location iterationBaseLocation = animationTask.getCurrentIterationBaseLocation();
        Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
        Vector3D sphereCenterVector3D = new Vector3D(sphereCenter.getX(), sphereCenter.getY(), sphereCenter.getZ());
        BoundingBox targetBoundingBox = target.getBoundingBox();
        Vector targetCenter = targetBoundingBox.getCenter();
        Vector3D targetCenterVector3D = new Vector3D(targetCenter.getX(), targetCenter.getY(), targetCenter.getZ());

        Vector u = animationTask.getCurrentU();
        Vector v = animationTask.getCurrentV();

        Plane circlePlane = new Plane(
                new Vector3D(iterationBaseLocation.getX(), iterationBaseLocation.getY(), iterationBaseLocation.getZ()),
                new Vector3D(u.getX(), u.getY(), u.getZ()), new Vector3D(v.getX(), v.getY(), v.getZ())
        );
        Point<Euclidean3D> projectedPoint = circlePlane.project(targetCenterVector3D);

        // If the projected point isn't in the circle
        if (projectedPoint.distance(sphereCenterVector3D) >= animationTask.getCurrentRadius()) return false;

        double radius = Math.sqrt(
                Math.pow(targetBoundingBox.getWidthX(), 2) +
                Math.pow(targetBoundingBox.getWidthZ(), 2) +
                Math.pow(targetBoundingBox.getHeight(), 2));

        double distance = projectedPoint.distance(targetCenterVector3D);
        // If the projected point is close to the entity
        return distance <= radius;
    });

    /**
     * Compute a generic version of an entity collision preset to check if an entity is inside a cube.
     * The computation is based on the given corners by computing the LOWER_WEST_NORTH and UPPER_EAST_SOUTH corners.
     *
     * @param firstCorner  The first corner of the cube
     * @param secondCorner The second corner of the cube
     * @return
     */
    public static EntityCollisionCheckPreset<?> compilePresetForGenericInsideCube(Location firstCorner, Location secondCorner) {
        return new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
            Location min = new Location(firstCorner.getWorld(),
                    Math.min(firstCorner.getX(), secondCorner.getX()),
                    Math.min(firstCorner.getY(), secondCorner.getY()),
                    Math.min(firstCorner.getZ(), secondCorner.getZ()));
            Location max = new Location(firstCorner.getWorld(),
                    Math.max(firstCorner.getX(), secondCorner.getX()),
                    Math.max(firstCorner.getY(), secondCorner.getY()),
                    Math.max(firstCorner.getZ(), secondCorner.getZ()));
            return BoundingBox.of(min, max).contains(target.getBoundingBox());
        });
    }

}
