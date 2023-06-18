package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.position.parent.ATrailPosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerData;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

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
    public void startTask() {
        if (taskId == null) {
            this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, trail.getCheckPeriod()).getTaskId();
        }
    }

    /***********GETTERS & SETTERS***********/

    public void addPlayer(UUID playerUUID) {
        int previousPlayersDataSize = this.playersDatas.size();
        this.playersDatas.put(playerUUID, new TrailPlayerData());
        if (previousPlayersDataSize == 0 && this.playersDatas.size() == 1) {
            startTask();
        }
    }

    public void removePlayer(UUID playerUUID) {
        int previousPlayersDataSize = this.playersDatas.size();
        this.playersDatas.remove(playerUUID);
        if (previousPlayersDataSize == 1 && this.playersDatas.size() == 0) {
            stopTask();
        }
    }

    public void clearPlayers() {
        this.playersDatas.clear();
        stopTask();
    }

    public boolean containsPlayer(UUID playerUUID) {
        return this.playersDatas.get(playerUUID) != null;
    }

    /**
     * Runs the task
     * <p>
     * Should not be called directly. The bukkit scheduler call this task itself.
     */
    @Override
    public void run() {

        // --- Handle trail duration end for each players
        if (!checkTrailDuration()) {
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
            List<TrailPlayerLocationData> locationsData = playerData.getPreviousLocations();

            int nbSavedLocations = locationsData.size();

            // --- Adding the new player location

            double distanceFromLastSavedLoc = Double.MAX_VALUE;
            Vector velocity = new Vector(0, 0, 0);
            Duration durationBetweenCurrentAndLastLoc = null;
            if (nbSavedLocations != 0) {
                final TrailPlayerLocationData lastTrailPlayerLocationData = locationsData.get(0);
                Location lastSavedLocation = lastTrailPlayerLocationData.getLocation();
                if (lastSavedLocation.getWorld() != null &&
                    Objects.equals(playerLoc.getWorld(), lastSavedLocation.getWorld())) {
                    distanceFromLastSavedLoc = lastSavedLocation.distance(playerLoc);
                    velocity = lastSavedLocation.toVector().subtract(playerLoc.toVector()).normalize();
                    durationBetweenCurrentAndLastLoc = Duration.between(lastTrailPlayerLocationData.getTime(), LocalTime.now());
                }
            }

            if (durationBetweenCurrentAndLastLoc != null) {
                velocity = velocity.multiply(1f / (
                        durationBetweenCurrentAndLastLoc.getSeconds() +
                        (durationBetweenCurrentAndLastLoc.getNano() / 1_000_000_000f)
                ));
            }

            playerData.addPreviousLocation(new TrailPlayerLocationData(
                    playerLoc,
                    distanceFromLastSavedLoc,
                    velocity,
                    player.getLocation().getDirection()
            ));
            nbSavedLocations++;

            //Finding the oldest location that fill the requirements
            Integer locationToShowIndex = findLocationToShowIndex(locationsData, nbSavedLocations);

            // --- show the animation
            if (locationToShowIndex != null) {
                showAnimation(player, locationsData, locationToShowIndex);
            }
        }
    }

    @Nullable
    private Integer findLocationToShowIndex(List<TrailPlayerLocationData> locationsData, int nbSavedLocations) {
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
        return locationToShowIndex;
    }

    private void showAnimation(Player player, List<TrailPlayerLocationData> locationsData, Integer locationToShowIndex) {
        final TrailPlayerLocationData trailPlayerData = locationsData.get(locationToShowIndex);

        //clear the list from oldest locations (the one that will be shown and all the older ones)
        locationsData.subList(locationToShowIndex - 1, locationsData.size()).clear();

        trail.getAnimations().forEach(animation -> {
            if (!animation.getPosition().getType().equals(PositionType.TRAIL)) {
                throw new IllegalStateException("During trail execution, the animation position type must be TRAIL. Please define a position that extends ATrailPosition.");
            }
            ATrailPosition trailPosition = (ATrailPosition) animation.getPosition();
            final AAnimation clonedAnimation = animation.clone();
            boolean shouldDisplay = trailPosition.computeFinalPositionAndRotation(clonedAnimation, player, trailPlayerData);
            if (shouldDisplay) {
                if (!clonedAnimation.getPosition().getType().equals(PositionType.NORMAL)) {
                    throw new IllegalStateException("During trail execution, the animation position should have been transformed to be NORMAL. Please define a position that extends ATrailPosition.");
                }
                clonedAnimation.show();
            }
        });
    }

    private boolean checkTrailDuration() {
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
            return false;
        }
        return true;
    }
}
