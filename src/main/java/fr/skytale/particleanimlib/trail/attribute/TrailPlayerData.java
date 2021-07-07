package fr.skytale.particleanimlib.trail.attribute;

import org.bukkit.Location;

import java.time.LocalTime;
import java.util.ArrayList;

public class TrailPlayerData {

    private ArrayList<TrailPlayerLocationData> locations;
    private LocalTime startTime;
    private Location lastLocation;
    private int iterationCount;

    public TrailPlayerData() {
        this.startTime = LocalTime.now();
        this.locations = new ArrayList<>();
        this.iterationCount = 0;
    }

    public ArrayList<TrailPlayerLocationData> getPreviousLocations() {
        return locations;
    }

    public void setPreviousLocations(ArrayList<TrailPlayerLocationData> locations) {
        this.locations = locations;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void addPreviousLocation(TrailPlayerLocationData trailPlayerLocationData) {
        locations.forEach(playerLocationData -> playerLocationData.addDistanceToPlayer(trailPlayerLocationData.getDistanceFromPreviousLocation()));
        locations.add(0, trailPlayerLocationData);
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }
}
