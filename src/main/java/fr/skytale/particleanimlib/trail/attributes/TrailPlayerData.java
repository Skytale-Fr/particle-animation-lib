package fr.skytale.particleanimlib.trail.attributes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.util.ArrayList;

public class TrailPlayerData {

    private ArrayList<TrailPlayerLocationData> locations;
    private LocalTime startTime;
    private Location lastLocation;

    public TrailPlayerData() {
        this.startTime = LocalTime.now();
        this.locations = new ArrayList<>();
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
}
