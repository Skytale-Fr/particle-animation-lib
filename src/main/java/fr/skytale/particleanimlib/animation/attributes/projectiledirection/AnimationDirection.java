package fr.skytale.particleanimlib.animation.attributes.projectiledirection;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public abstract class AnimationDirection {

    public static class MoveData {
        public static MoveData createError() {
            return new MoveData(null, false, true);
        }

        public static MoveData createMove(Vector move) {
            return new MoveData(move, false, false);
        }

        public static MoveData createReachingMove(Vector move){
            return new MoveData(move, true, false);
        }

        public final Vector move;
        public final boolean willReachTarget;
        public final boolean hasError;

        protected MoveData(Vector move, boolean willReachTarget, boolean hasError) {
            this.move = move;
            this.willReachTarget = willReachTarget;
            this.hasError = hasError;
        }
    }

    public enum Type {
        MOVE_VECTOR, TARGET_LOCATION, TARGET_ENTITY;
    }

    public static AnimationDirection fromMoveVector(IVariable<Vector> moveVector) {
        return new VectorAnimationDirection(moveVector);
    }

    public static AnimationDirection fromTargetLocation(IVariable<Location> targetLocation, IVariable<Double> speed) {
        return new LocationAnimationDirection(targetLocation, speed);
    }

    public static AnimationDirection fromTargetEntity(Entity targetEntity, IVariable<Double> speed) {
        return new EntityAnimationDirection(targetEntity, speed);
    }

    private IVariable<Vector> moveVector;
    private IVariable<Location> targetLocation;
    private final Entity targetEntity;
    private IVariable<Double> speed;
    private final Type type;

    protected AnimationDirection(IVariable<Vector> moveVector, IVariable<Location> targetLocation, Entity targetEntity, IVariable<Double> speed, Type type) {
        this.moveVector = moveVector;
        this.targetLocation = targetLocation;
        this.targetEntity = targetEntity;
        this.speed = speed;
        this.type = type;
    }

    public IVariable<Vector> getMoveVector() {
        return moveVector;
    }

    public IVariable<Location> getTargetLocation() {
        return targetLocation;
    }

    public Entity getTargetEntity() {
        return targetEntity;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns null if moveVector cannot be calculated or if we (almost) reached the target
     * @param currentLocation the current location
     * @param iterationCount the iteration count
     * @return the move vector or null if moveVector cannot be calculated or if we (almost) reached the target.
     */
    public MoveData getMoveVector(Location currentLocation, int iterationCount) {
        switch (type) {
            case MOVE_VECTOR:
                return MoveData.createMove(moveVector.getCurrentValue(iterationCount).clone());
            case TARGET_ENTITY:
                if (!targetEntity.isValid())
                    return MoveData.createError();

                Location entityLocation = targetEntity.getLocation();

                if (entityLocation.getWorld() != currentLocation.getWorld())
                    return MoveData.createError();

                Vector pathVector = entityLocation.toVector().subtract(currentLocation.toVector());

                Vector moveVector = pathVector.normalize().multiply(speed.getCurrentValue(iterationCount));

                if (pathVector.length() < moveVector.length()) {
                    return MoveData.createReachingMove(pathVector);
                }

                return MoveData.createMove(moveVector);
            case TARGET_LOCATION:
                Location location = targetLocation.getCurrentValue(iterationCount);

                if (location.getWorld() != currentLocation.getWorld())
                    return MoveData.createError();

                Vector pathVector2 = location.toVector().subtract(currentLocation.toVector());

                Vector moveVector2 = pathVector2.normalize().multiply(speed.getCurrentValue(iterationCount));

                if (pathVector2.length() < moveVector2.length()) {
                    return MoveData.createReachingMove(pathVector2);
                }

                return MoveData.createMove(moveVector2);
            default:
                throw new NotImplementedException("This type of AnimationDirection is not implemented yet");
        }
    }

    @Override
    public AnimationDirection clone() {
        AnimationDirection obj = null;
        try {
            obj = (AnimationDirection) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.moveVector = this.moveVector == null ? null : this.moveVector.copy();
        obj.speed = this.speed == null ? null : this.speed.copy();
        obj.targetLocation = this.targetLocation == null ? null : this.targetLocation.copy();
        return obj;
    }


}
