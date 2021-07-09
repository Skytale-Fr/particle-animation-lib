package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;

public abstract class SubAnimPointDefinition extends PointDefinition {

    protected SubAnimPointDefinition(ShowMethodParameters showMethodParameters) {
        super(showMethodParameters);
    }

    public abstract ISubAnimation getSubAnimation();
}
