package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;

@FunctionalInterface
public interface CollisionPredicate<T, K extends AAnimationTask> {

    public boolean test(Location location, K animationTask, T target);

}
