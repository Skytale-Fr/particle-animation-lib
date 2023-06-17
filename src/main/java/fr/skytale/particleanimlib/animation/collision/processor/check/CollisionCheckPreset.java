package fr.skytale.particleanimlib.animation.collision.processor.check;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * This class describes a preset of a collision predicate.
 *
 * @param <T> The type of target you want to perform collisions on
 */
public class CollisionCheckPreset<T> {

    private CollisionCheckPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionCheckPredicate;

    public CollisionCheckPreset(CollisionCheckPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionCheckPredicate) {
        this.collisionCheckPredicate = collisionCheckPredicate;
    }

    /**
     * Returns the collision predicate set in this preset
     *
     * @param <K> The type of animation task you want to plug this collision predicate to
     * @return The collision predicate
     */
    public <K extends AAnimationTask<? extends AAnimation>> CollisionCheckPredicate<T, K> getCollisionPredicate() {
        return (CollisionCheckPredicate<T, K>) collisionCheckPredicate;
    }

}
