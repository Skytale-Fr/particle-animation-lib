package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.PointShowCallback;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CallbackPointDefinition implements APointDefinition {

    private final PointShowCallback pointShowCallback;

    /**
     * Builds a callback point definition
     * @param pointShowCallback the callback to use when displaying points
     */
    public CallbackPointDefinition(PointShowCallback pointShowCallback) {
        this.pointShowCallback = pointShowCallback;
    }

    /**
     * Retrieves the callback
     * @return the callback
     */
    public PointShowCallback getPointShowCallback() {
        return pointShowCallback;
    }

    /**
     * Displays the particle at the given point location
     * @param pointLocation                         The location of the point to show
     * @param animation                             The animation whose point is displayed
     * @param task                                  The animation task that calls this method
     * @param fromAnimCenterToPoint                 A vector that goes:
     *                                              - From the parent animation center (its current iteration base location)
     *                                              - To the point to show
     * @param fromPreviousToCurrentAnimBaseLocation A vector that goes:
     *                                              - From the parent animation previous base location (of the previous iteration)
     *                                              - To the parent animation current base location (of the current iteration)
     */
    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        pointShowCallback.show(pointLocation, animation, task, fromAnimCenterToPoint, fromPreviousToCurrentAnimBaseLocation);
    }

    /**
     * Clone the particle point definition
     * @return a cloned particle point definition
     */
    @Override
    public APointDefinition copy() {
        // we cannot clone a callback (which is the only attribute of this class instances)
        // so better return the same instance without any copy.
        return this;
    }
}
