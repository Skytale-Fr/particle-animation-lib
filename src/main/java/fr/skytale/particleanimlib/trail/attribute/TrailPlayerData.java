package fr.skytale.particleanimlib.trail.attribute;

import org.bukkit.Location;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TrailPlayerData {

    private List<TrailPlayerLocationData> locations;
    private LocalTime startTime;

    public TrailPlayerData() {
        this.startTime = LocalTime.now();
        this.locations = new ArrayList<>();
    }

    public List<TrailPlayerLocationData> getPreviousLocations() {
        return locations;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void addPreviousLocation(TrailPlayerLocationData trailPlayerLocationData) {
        locations.forEach(playerLocationData -> playerLocationData.addDistanceToPlayer(trailPlayerLocationData.getDistanceFromPreviousLocation()));
        locations.add(0, trailPlayerLocationData);
    }
}
