package fr.skytale.particleanimlib.animation.attributes.position;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public abstract class APosition implements Cloneable {

    public static enum Type {
        LOCATION,
        ENTITY,
        TRAIL
    }

    public static APosition fromLocation(IVariable<Location> location) {
        return new LocationPosition(location);
    }

    public static APosition fromEntity(Entity movingEntity, IVariable<Vector> relativeLocation) {
        return new EntityPosition(movingEntity, relativeLocation);
    }

    public static APosition fromEntity(Entity movingEntity) {
        return new EntityPosition(movingEntity);
    }

    public static APosition fromTrail(IVariable<Vector> relativeLocation) {
        return new TrailPosition(relativeLocation);
    }

    protected Type type;
    protected IVariable<Location> location;
    protected Entity movingEntity;
    protected IVariable<Vector> relativeLocation;

    public IVariable<Location> getLocation() {
        return location;
    }

    public Entity getMovingEntity() {
        return movingEntity;
    }

    public IVariable<Vector> getRelativeLocation() {
        return relativeLocation;
    }

    public Type getType() {
        return type;
    }

    @Override
    public APosition clone() {
        APosition obj = null;
        try {
            obj = (APosition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.location = this.location == null ? null : this.location.copy();
        obj.relativeLocation = this.getRelativeLocation() == null ? null : this.getRelativeLocation().copy();
        return obj;
    }
}
