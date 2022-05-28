package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@FunctionalInterface
    public interface PointShowCallback {

        /**
         * Performs this operation on the given arguments.
         * Called when a point should be shown
         *
         * @param animation the animation
         * @param location the location
         * @param fromCenterToPoint the direction vector
         * @param parentAnimationTask the parent animation task
         */
        void show(AAnimation animation, Location location, Vector fromCenterToPoint, AAnimationTask<?> parentAnimationTask);

    }
