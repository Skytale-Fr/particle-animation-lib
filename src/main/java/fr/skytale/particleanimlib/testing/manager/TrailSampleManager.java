package fr.skytale.particleanimlib.testing.manager;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import fr.skytale.particleanimlib.trail.TrailTask;
import fr.skytale.particleanimlib.trail.attribute.TrailPreset;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TrailSampleManager {

    public static final String NOT_IMPLEMENTED = "This preset does not exist. Please implement the required code in \"fr/skytale/particleanimlib/trail/attribute/TrailPreset.java\"";

    public static TrailSampleManager instance = null;
    private final Map<String, TrailTask> trailTasksByPresetName;

    private TrailSampleManager() {
        trailTasksByPresetName = new HashMap<>();
    }

    public static TrailSampleManager getInstance() {
        if (instance == null) {
            instance = new TrailSampleManager();
        }
        return instance;
    }

    public void enableTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = getTrailTask(player, trailAnimationSampleName);
        UUID playerUUID = player.getUniqueId();
        if (!trailTask.containsPlayer(playerUUID)) {
            trailTask.addPlayer(playerUUID);
        }

    }

    public void disableTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = getTrailTask(player, trailAnimationSampleName);
        UUID playerUUID = player.getUniqueId();
        if (trailTask.containsPlayer(playerUUID)) {
            trailTask.removePlayer(playerUUID);
        }
    }

    public boolean toggleTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = getTrailTask(player, trailAnimationSampleName);
        UUID playerUUID = player.getUniqueId();
        if (trailTask.containsPlayer(playerUUID)) {
            trailTask.removePlayer(playerUUID);
            return false;
        } else {
            trailTask.addPlayer(playerUUID);
            return true;
        }
    }

    private TrailTask getTrailTask(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = trailTasksByPresetName.get(trailAnimationSampleName);
        if (trailTask == null) {
            try {
                //Try to initialize task
                TrailPreset preset = TrailPreset.fromName(trailAnimationSampleName);
                trailTask = preset.createBuilder(ParticleAnimLibTest.getInstance().getPlugin()).getTrail().getTrailTask();
                trailTasksByPresetName.put(preset.name(), trailTask);
            } catch (IllegalArgumentException e) {
                player.sendMessage(NOT_IMPLEMENTED);
                throw new NotImplementedException(NOT_IMPLEMENTED, e);
            }
        }
        return trailTask;
    }

    public Set<String> getTrailNames() {
        return Arrays.stream(TrailPreset.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
