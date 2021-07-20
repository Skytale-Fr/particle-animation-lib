package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerData;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class TrailTask implements Runnable {

    private final Map<UUID, TrailPlayerData> playersDatas;

    private final Trail trail;

    private final JavaPlugin plugin;

    //Evolving attributes

    private Integer taskId = null;

    public TrailTask(Trail trail) {
        this.playersDatas = new HashMap<>();
        this.trail = trail.clone();
        this.plugin = trail.getAnimations().stream().findAny().orElseThrow(IllegalStateException::new).getPlugin();
    }

    /**
     * Stops the task
     * <p>
     * The task stopping is already done automatically when removing the last player
     *
     * @see #removePlayer(UUID)
     * @see #clearPlayers()
     */
    @Deprecated
    public void stopTask() {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
        }
    }


    /**
     * Starts the task
     * <p>
     * The task starting is already done automatically when adding the first player
     *
     * @see #addPlayer(UUID)
     */
    @Deprecated
    public void startTask() {
        if (taskId == null) {
            this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, trail.getCheckPeriod()).getTaskId();
        }
    }

    /***********GETTERS & SETTERS***********/

    public void addPlayer(UUID playerUUID) {
        if (this.playersDatas.size() == 0) startTask();
        this.playersDatas.put(playerUUID, new TrailPlayerData()); //can also be used to reset trail duration
    }

    public boolean removePlayer(UUID playerUUID) {
        boolean result = this.playersDatas.remove(playerUUID) != null;
        if (this.playersDatas.size() == 0) stopTask();
        return result;
    }

    public void clearPlayers() {
        this.playersDatas.clear();
        stopTask();
    }

    public boolean containsPlayer(UUID playerUUID) {
        return this.playersDatas.containsKey(playerUUID);
    }

    /**
     * Runs the task
     * <p>
     * Should not be called directly. The bukkit scheduler call this task itself.
     */
    @Deprecated
    @Override
    public void run() {

        // --- Handle trail duration end for each players

        if (trail.getDuration() != null) {
            playersDatas.entrySet().removeIf(entry -> {
                Duration fromStartDuration = Duration.between(entry.getValue().getStartTime(), LocalTime.now());
                if (fromStartDuration.compareTo(trail.getDuration()) > 0) {
                    if (trail.getCallback() != null) {
                        trail.getCallback().run(trail, entry.getKey());
                    }
                    return true;
                }
                return false;
            });
        }

        if (playersDatas.size() == 0) {
            stopTask();
            return;
        }

        // --- Looping over each player to save their path and create trails

        for (Map.Entry<UUID, TrailPlayerData> entry : playersDatas.entrySet()) {
            UUID playerUuid = entry.getKey();
            TrailPlayerData playerData = entry.getValue();
            Player player = Bukkit.getPlayer(playerUuid);
            if (player == null || !player.isOnline() || player.isDead()) {
                continue;
            }
            Location playerLoc = player.getLocation();
            ArrayList<TrailPlayerLocationData> locationsData = playerData.getPreviousLocations();

            int nbSavedLocations = locationsData.size();

            // --- Adding the new player location

            double distanceFromLastSavedLoc = Double.MAX_VALUE;
            if (nbSavedLocations != 0) {
                Location lastSavedLocation = locationsData.get(0).getLocation();
                if (lastSavedLocation.getWorld() != null && Objects.equals(playerLoc.getWorld(), lastSavedLocation.getWorld())) {
                    distanceFromLastSavedLoc = lastSavedLocation.distance(playerLoc);
                }
            }

            playerData.addPreviousLocation(new TrailPlayerLocationData(playerLoc, distanceFromLastSavedLoc));
            nbSavedLocations++;

            //Finding the oldest location that fill the requirements
            Integer locationToShowIndex = null;
            double distanceFromLastShow = 0;
            for (int i = nbSavedLocations - 1; i > 0; i--) {
                TrailPlayerLocationData prevPlayerLoc = locationsData.get(i);

                distanceFromLastShow = TrailPlayerLocationData.addSafe(distanceFromLastShow, prevPlayerLoc.getDistanceFromPreviousLocation());

                double distanceToPlayer = prevPlayerLoc.getDistanceToPlayer();

                // If the distance ran by the player is too short
                if (distanceToPlayer < trail.getMinPlayerToAnimationDistance()) {
                    continue;
                }

                // If the distance between this location and the previous shown location is too short
                if (distanceFromLastShow < trail.getMinDistanceBetweenAnimations()) {
                    continue;
                }

                //The animation will be show at this location
                locationToShowIndex = i;
                break;
            }

            // --- show the animation
            int iterationCount = playerData.getIterationCount();
            if (locationToShowIndex != null) {
                Location locationToShow = locationsData.get(locationToShowIndex).getLocation();

                //clear the list from oldest locations (the one that will be shown and all the older ones)
                locationsData.subList(locationToShowIndex - 1, locationsData.size()).clear();

                showAnimations(locationToShow, iterationCount);
            }
            playerData.setIterationCount(iterationCount + 1);
        }
    }

    private void showAnimations(Location locationToShow, int iterationCount) {
        trail.getAnimations().forEach(animation -> {
            APosition trailPosition = animation.getPosition();
            Vector relativeLocation = trailPosition.getRelativeLocation().getCurrentValue(iterationCount);
            animation.setPosition(APosition.fromLocation(new Constant<>(locationToShow.clone().add(relativeLocation))));
            animation.show();
            //redefine trailPosition is order to keep the relative location data inside it for the next execution
            animation.setPosition(trailPosition);
        });
    }
}
