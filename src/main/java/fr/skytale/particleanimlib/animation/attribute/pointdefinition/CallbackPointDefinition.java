package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.PointShowCallback;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CallbackPointDefinition implements APointDefinition {

    private final PointShowCallback pointShowCallback;

    public CallbackPointDefinition(PointShowCallback pointShowCallback) {
        this.pointShowCallback = pointShowCallback;
    }

    public PointShowCallback getPointShowCallback() {
        return pointShowCallback;
    }

    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        pointShowCallback.show(pointLocation, animation, task, fromAnimCenterToPoint, fromPreviousToCurrentAnimBaseLocation);
    }

    @Override
    public APointDefinition copy() {
        // we cannot clone a callback (which is the only attribute of this class instances)
        // so better return the same instance without any copy.
        return this;
    }
}
