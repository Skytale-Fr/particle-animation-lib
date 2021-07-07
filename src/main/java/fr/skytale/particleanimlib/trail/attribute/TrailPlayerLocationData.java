package fr.skytale.particleanimlib.trail.attribute;

import org.bukkit.Location;

public class TrailPlayerLocationData {

    private Location location;
    private double distanceFromPreviousLocation;
    private double distanceToPlayer;

    public TrailPlayerLocationData() {
    }

    public TrailPlayerLocationData(Location location, double distanceFromPreviousLocation) {
        this.location = location;
        this.distanceFromPreviousLocation = distanceFromPreviousLocation;
        this.distanceToPlayer = 0;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getDistanceFromPreviousLocation() {
        return distanceFromPreviousLocation;
    }

    public void setDistanceFromPreviousLocation(double distanceFromPreviousLocation) {
        this.distanceFromPreviousLocation = distanceFromPreviousLocation;
    }

    public double getDistanceToPlayer() {
        return distanceToPlayer;
    }

    public void addDistanceToPlayer(double distanceToPlayerToAdd) {
        distanceToPlayer = addSafe(distanceToPlayer, distanceToPlayerToAdd);
    }

    public static double addSafe(double a, double b) {
        return a == Double.MAX_VALUE || b == Double.MAX_VALUE ? Double.MAX_VALUE : a + b;
    }
}
