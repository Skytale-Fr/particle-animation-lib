package fr.skytale.particleanimlib.animation.attributes.position;

import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityPosition extends APosition {

    public EntityPosition(Entity movingEntity, IVariable<Vector> relativeLocation) {
        this.type = Type.ENTITY;
        if (movingEntity == null) {
            throw new IllegalArgumentException("MovingEntity should not be null");
        }
        if (relativeLocation == null) {
            this.relativeLocation = new Constant<>(new Vector(0, 0, 0));
        }
        this.movingEntity = movingEntity;
        this.relativeLocation = relativeLocation;
    }

    public EntityPosition(Entity movingEntity) {
        this(movingEntity, null);
    }
}
