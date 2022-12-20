package fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@FunctionalInterface
public interface PointShowCallback {

    /**
     * Show a point of an animation
     *
     * @param pointLocation                         The location of the point to show
     * @param animation                             The animation whose point is displayed
     * @param task                                  The animation task that calls this method
     * @param fromAnimCenterToPoint                 A vector that goes:
     *                                              - From the parent animation center (its current iteration base location)
     *                                              - To the point to show
     * @param fromPreviousToCurrentAnimBaseLocation A vector that goes:
     *                                              - From the parent animation previous base location (of the previous iteration)
     *                                              - To the parent animation current base location
     */
    void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation);

}
