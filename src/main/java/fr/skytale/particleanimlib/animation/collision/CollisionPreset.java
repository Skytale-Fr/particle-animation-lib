package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * This class describes a preset of a collision predicate.
 *
 * @param <T> The type of target you want to perform collisions on
 */
public class CollisionPreset<T> {

    private CollisionPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionPredicate;

    public CollisionPreset(CollisionPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionPredicate) {
        this.collisionPredicate = collisionPredicate;
    }

    /**
     * Returns the collision predicate set in this preset
     *
     * @param <K> The type of animation task you want to plug this collision predicate to
     * @return The collision predicate
     */
    public <K extends AAnimationTask<? extends AAnimation>> CollisionPredicate<T, K> getCollisionPredicate() {
        return (CollisionPredicate<T, K>) collisionPredicate;
    }

}
