package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * This processor extends from CollisionProcessor and should provide animation's location.
 * For instance, if the animation is a sphere, the provided location should be the center of the sphere.
 *
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug this collision processor to
 * @see CollisionProcessor
 */
public class SimpleCollisionProcessor<T, K extends AAnimationTask<? extends AAnimation>> extends CollisionProcessor<T, K> {

    /**
     * Creates a simple collision processor with the provided collision check predicate and the action callback.
     *
     * @param collisionTest  The collision predicate
     * @param actionCallback The action callback
     */
    public SimpleCollisionProcessor(CollisionPredicate<T, K> collisionTest, CollisionActionCallback<T, K> actionCallback) {
        super(CollisionTestType.PER_PARTICLE, collisionTest, actionCallback);
    }

    /**
     * Creates a simple collision processor from the provided animation builder (to fetch the related animation task), the collision preset and the action callbacK.
     *
     * @param builder        The animation builder
     * @param preset         The collision preset
     * @param actionCallback The action callback
     * @param <T>            The type of target you want to perform collisions on
     * @param <K>            The type of animation task you want to plug this collision processor to
     * @return An instance of particle collision processor
     */
    public static <T, K extends AAnimationTask<? extends AAnimation>> SimpleCollisionProcessor<T, K> useDefault(AAnimationBuilder<?, K> builder, CollisionPreset<T> preset, CollisionActionCallback<T, K> actionCallback) {
        return new SimpleCollisionProcessor<T, K>(preset.<K>getCollisionPredicate(), actionCallback);
    }

}
