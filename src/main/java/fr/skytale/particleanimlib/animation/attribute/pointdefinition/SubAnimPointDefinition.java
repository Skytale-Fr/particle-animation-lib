package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.ISubAnimation;

public abstract class SubAnimPointDefinition extends APointDefinition {

    protected SubAnimPointDefinition(ShowMethodParameters showMethodParameters) {
        super(showMethodParameters, true);
    }

    public abstract ISubAnimation getSubAnimation();
}
