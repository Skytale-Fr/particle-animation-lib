package fr.skytale.particleanimlib.trail.attribute;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class TrailPlayerLocationData {

    private final Vector eyeDirection;
    private final Vector velocity;
    private final Location location;
    private final double distanceFromPreviousLocation;
    private double distanceToPlayer;

    public TrailPlayerLocationData(Location location, double distanceFromPreviousLocation, Vector eyeDirection, Vector velocity) {
        this.location = location;
        this.distanceFromPreviousLocation = distanceFromPreviousLocation;
        this.distanceToPlayer = 0;
        this.eyeDirection = eyeDirection;
        this.velocity = velocity;
    }

    public static double addSafe(double a, double b) {
        return a == Double.MAX_VALUE || b == Double.MAX_VALUE ? Double.MAX_VALUE : a + b;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistanceFromPreviousLocation() {
        return distanceFromPreviousLocation;
    }

    public double getDistanceToPlayer() {
        return distanceToPlayer;
    }

    public Vector getEyeDirection() {
        return eyeDirection;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void addDistanceToPlayer(double distanceToPlayerToAdd) {
        distanceToPlayer = addSafe(distanceToPlayer, distanceToPlayerToAdd);
    }
}
