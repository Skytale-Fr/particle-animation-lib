package fr.skytale.particleanimlib.animation.attribute.projectiledirection;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.entity.Entity;

public class EntityAnimationDirection extends AnimationDirection {

    public EntityAnimationDirection(Entity targetEntity, IVariable<Double> speed) {
        super(
                null,
                null,
                targetEntity,
                speed,
                Type.TARGET_ENTITY
        );
    }
}
