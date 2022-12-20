package fr.skytale.particleanimlib.animation.attribute.position.attr;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Objects;

public class AnimationMove {

    // The location reached by this move
    private final Location afterMoveLocation;
    // A vector representing the move from the previous location to this one
    private final Vector move;
    // If this move will make the projectile reach the target
    private final boolean hasReachedTarget;
    // If the projectile should disappear (because the target is dead or left the world)
    private final boolean cancelled;

    //Modifier ce constructeur et les méthodes statiques
    protected AnimationMove(Location afterMoveLocation, Vector move, boolean hasReachedTarget, boolean cancelled) {
        this.afterMoveLocation = afterMoveLocation;
        this.move = move;
        this.hasReachedTarget = hasReachedTarget;
        this.cancelled = cancelled;
    }

    public static AnimationMove createError() {
        return new AnimationMove(null, null, false, true);
    }

    public static AnimationMove createMove(Location previousLocation, Vector move) {
        return AnimationMove.createMove(previousLocation, move, false);
    }

    public static AnimationMove createMove(Location previousLocation, Location newLocation) {
        return AnimationMove.createMove(previousLocation, newLocation, false);
    }

    public static AnimationMove createMove(Location previousLocation, Vector move, boolean willReachTarget) {
        return new AnimationMove(previousLocation.clone().add(move), move, willReachTarget, false);
    }

    public static AnimationMove createMove(Location previousLocation, Location newLocation, boolean willReachTarget) {
        return new AnimationMove(newLocation, newLocation.toVector().subtract(previousLocation.toVector()), willReachTarget, false);
    }

    public static AnimationMove createOriginMove(Location location) {
        return new AnimationMove(location, new Vector(0, 0, 0), false, false);
    }

    public Location getAfterMoveLocation() {
        return afterMoveLocation;
    }

    public Vector getMove() {
        return move;
    }

    public boolean hasReachedTarget() {
        return hasReachedTarget;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public int hashCode() {
        int result = afterMoveLocation != null ? afterMoveLocation.hashCode() : 0;
        result = 31 * result + (move != null ? move.hashCode() : 0);
        result = 31 * result + (hasReachedTarget ? 1 : 0);
        result = 31 * result + (cancelled ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimationMove that = (AnimationMove) o;

        if (hasReachedTarget != that.hasReachedTarget) return false;
        if (cancelled != that.cancelled) return false;
        if (!Objects.equals(afterMoveLocation, that.afterMoveLocation)) return false;
        return Objects.equals(move, that.move);
    }
}