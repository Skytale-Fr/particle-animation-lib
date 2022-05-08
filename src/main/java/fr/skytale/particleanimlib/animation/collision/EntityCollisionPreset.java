package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereTask;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

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
        Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
        Vector targetCenter = target.getBoundingBox().getCenter();
        double radius = animationTask.getCurrentRadius();
        Vector paddingVector = new Vector(radius, 0, radius);
        double distance = Math.hypot(targetCenter.getX() - sphereCenter.getX(), targetCenter.getZ() - sphereCenter.getZ());
        return distance <= radius && target.getBoundingBox().overlaps(
                BoundingBox.of(sphereCenter.clone().subtract(paddingVector), sphereCenter.clone().add(paddingVector))
        );
    });

    /**
     * Creates an entity collision preset with the provided collision check predicate.
     * @param collisionPredicate The collision check predicate.
     */
    public EntityCollisionPreset(CollisionPredicate<Entity, K> collisionPredicate) {
        super(collisionPredicate);
    }

}
