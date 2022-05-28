package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereTask;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.Point;
import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Arrays;

/**
 * This class extends CollisionPreset to implements entity collision presets.
 * @param <K> The type of animation task you want to plug this collision preset to
 * @see CollisionPreset
 */
public class EntityCollisionPreset<K extends AAnimationTask<? extends AAnimation>> extends CollisionPreset<Entity> {

    /**
     * This preset checks if the entity collides with a particle.<br />
     * This preset works with every type of animation. <br />
     * The verification is made with the bounding box of the entity and should be exact.
     */
    public static final EntityCollisionPreset<? extends AAnimationTask> EXACT_BOUNDING_BOX = new EntityCollisionPreset<>((location, animationTask, target) -> {
        BoundingBox entityBoundingBox = target.getBoundingBox();
        return entityBoundingBox.contains(location.getX(), location.getY(), location.getZ());
    });

    /**
     * This preset checks if the entity is inside a cuboid. <br />
     * This preset works with only the Cuboid animation. <br />
     * The verification is made with the bounding box of the entity and the one of the cuboid. So it should be exact.
     */
    public static final EntityCollisionPreset<CuboidTask> EXACT_BOUNDING_BOX_INSIDE_CUBOID = new EntityCollisionPreset<>((location, animationTask, target) -> {
        Location min = animationTask.getCurrentCornersLocation().get(CuboidTask.CuboidCorner.LOWER_WEST_NORTH);
        Location max = animationTask.getCurrentCornersLocation().get(CuboidTask.CuboidCorner.UPPER_EAST_SOUTH);
        return BoundingBox.of(min, max).contains(target.getBoundingBox());
    });

    /**
     * This preset checks if the entity is inside a sphere.<br />
     * This preset works with only the Sphere animation. <br />
     * The verification is made with the center of the entity's bounding box. This way, the check isn't exact.
     */
    public static final EntityCollisionPreset<SphereTask> TARGET_CENTER_INSIDE_SPHERE = new EntityCollisionPreset<>((location, animationTask, target) -> {
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
    public static final EntityCollisionPreset<CircleTask> TARGET_CENTER_INSIDE_CIRCLE = new EntityCollisionPreset<>((location, animationTask, target) -> {
        Location iterationBaseLocation = animationTask.getCurrentIterationBaseLocation();
        Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
        BoundingBox targetBoundingBox = target.getBoundingBox();
        Vector targetCenter = targetBoundingBox.getCenter();
        Vector3D targetCenterVector3D = new Vector3D(targetCenter.getX(), targetCenter.getY(), targetCenter.getZ());

        Vector u = animationTask.getCurrentAbsoluteU();
        Vector v = animationTask.getCurrentAbsoluteV();

        Plane circlePlane = new Plane(new Vector3D(iterationBaseLocation.getX(), iterationBaseLocation.getY(), iterationBaseLocation.getZ()), new Vector3D(u.getX(), u.getY(), u.getZ()), new Vector3D(v.getX(), v.getY(), v.getZ()));
        Point<Euclidean3D> projectedPoint = circlePlane.project(targetCenterVector3D);

        double radius = Math.sqrt(Math.pow(targetBoundingBox.getWidthX(), 2) + Math.pow(targetBoundingBox.getWidthZ(), 2) + Math.pow(targetBoundingBox.getHeight(), 2));

        double distance = projectedPoint.distance(targetCenterVector3D);
        return Math.abs(distance - radius) <= 0.2d;
    });

    /**
     * Creates an entity collision preset with the provided collision check predicate.
     * @param collisionPredicate The collision check predicate.
     */
    public EntityCollisionPreset(CollisionPredicate<Entity, K> collisionPredicate) {
        super(collisionPredicate);
    }

    /**
     * Compute a generic version of an entity collision preset to check if an entity is inside a sphere.
     * The computation is based on the given sphere radius and on the animation current iteration base location.
     * @param sphereRadius The radius of the sphere
     * @return
     */
    public static EntityCollisionPreset<?> compilePresetForGenericInsideSphere(double sphereRadius) {
        return new EntityCollisionPreset<>((location, animationTask, target) -> {
            double radius = sphereRadius;
            Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
            Vector targetCenter = target.getBoundingBox().getCenter();
            return targetCenter.isInSphere(sphereCenter, radius);
        });
    }

    /**
     * Compute a generic version of an entity collision preset to check if an entity is inside a cube.
     * The computation is based on the given corners by computing the LOWER_WEST_NORTH and UPPER_EAST_SOUTH corners.
     * @param firstCorner The first corner of the cube
     * @param secondCorner The second corner of the cube
     * @return
     */
    public static EntityCollisionPreset<?> compilePresetForGenericInsideCube(Location firstCorner, Location secondCorner) {
        return new EntityCollisionPreset<>((location, animationTask, target) -> {
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
