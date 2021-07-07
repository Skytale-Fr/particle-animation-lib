package fr.skytale.particleanimlib.animation.attribute.position;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityPosition extends APosition {

    public EntityPosition(Entity movingEntity, IVariable<Vector> relativeLocation) {
        this.type = Type.ENTITY;
        if (movingEntity == null) {
            throw new IllegalArgumentException("MovingEntity should not be null");
        }
        this.relativeLocation = relativeLocation == null ? new Constant<>(new Vector(0, 0, 0)) : relativeLocation;
        this.movingEntity = movingEntity;
    }

    public EntityPosition(Entity movingEntity, Vector relativeLocation) {
        this(movingEntity, new Constant<>(relativeLocation));
    }

    public EntityPosition(Entity movingEntity) {
        this(movingEntity, new Vector(0, 0, 0));
    }
}
