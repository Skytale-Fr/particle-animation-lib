package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;

/**
 * This function should return true if the target collides a region you "set" in.
 *
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug this collision predicate to
 */
@FunctionalInterface
public interface CollisionPredicate<T, K extends AAnimationTask> {

    public boolean test(Location location, K animationTask, T target);

}
