package fr.skytale.particleanimlib.animation.collision.processor.check;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * This class extends CollisionPreset to implements entity collision presets.
 *
 * @param <K> The type of animation task you want to plug this collision preset to
 * @see CollisionCheckPreset
 */
public class EntityCollisionCheckPreset<K extends AAnimationTask<? extends AAnimation>> extends CollisionCheckPreset<Entity> {

    /**
     * Creates an entity collision preset with the provided collision check predicate.
     *
     * @param collisionCheckPredicate The collision check predicate.
     */
    public EntityCollisionCheckPreset(CollisionCheckPredicate<Entity, K> collisionCheckPredicate) {
        super(collisionCheckPredicate);
    }

    /**
     * Compute a generic version of an entity collision preset to check if an entity is inside a sphere.
     * The computation is based on the given sphere radius and on the animation current iteration base location.
     *
     * @param sphereRadius The radius of the sphere
     * @return
     */
    public static EntityCollisionCheckPreset<?> compilePresetForGenericInsideSphere(double sphereRadius) {
        return new EntityCollisionCheckPreset<>((location, animationTask, target) -> {
            Vector sphereCenter = animationTask.getCurrentIterationBaseLocation().toVector();
            Vector targetCenter = target.getBoundingBox().getCenter();
            return targetCenter.isInSphere(sphereCenter, sphereRadius);
        });
    }

}
