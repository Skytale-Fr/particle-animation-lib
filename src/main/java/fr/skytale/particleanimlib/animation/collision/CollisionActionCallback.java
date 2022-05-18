package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * The run() method will be called if the collision check is verified.
 * This function should return an integer represents how many ticks a target can't be part of the collision process
 * since the last time it collides.
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug this collision action callback to
 * @see CollisionBuilder
 * @see CollisionHandler
 */
@FunctionalInterface
public interface CollisionActionCallback<T, K extends AAnimationTask> {

    /**
     * The run() method will be called if the collision check is verified.
     * This function should return an integer represents how many ticks a target can't be part of the collision process
     * since the last time it collides.
     * @param animationTask The animation task that calls the method
     * @param target The target that verifies the collision check predicate
     * @return
     */
    public int run(K animationTask, T target);

}
