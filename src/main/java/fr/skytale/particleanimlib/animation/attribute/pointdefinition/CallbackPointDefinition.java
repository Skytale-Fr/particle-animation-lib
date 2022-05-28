package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CallbackPointDefinition extends APointDefinition {

    private PointShowCallback pointShowCallback;

    public CallbackPointDefinition(PointShowCallback pointShowCallback) {
        super(ShowMethodParameters.LOCATION_AND_DIRECTION, false);
        this.pointShowCallback = pointShowCallback;
    }

    public PointShowCallback getPointShowCallback() {
        return pointShowCallback;
    }

    @Override
    public void show(AAnimation animation, Location loc, AAnimationTask<?> parentTask) {
        pointShowCallback.show(animation, loc, null, parentTask);
    }

    @Override
    public void show(AAnimation animation, Location loc, Vector fromCenterToPoint, AAnimationTask<?> parentTask) {
        pointShowCallback.show(animation, loc, fromCenterToPoint, parentTask);
    }

    @Override
    public CallbackPointDefinition clone() {
        CallbackPointDefinition obj = (CallbackPointDefinition) super.clone();
        obj.pointShowCallback = this.pointShowCallback;
        return obj;
    }

}
