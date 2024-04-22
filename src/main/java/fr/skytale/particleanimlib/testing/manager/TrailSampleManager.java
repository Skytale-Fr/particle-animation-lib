package fr.skytale.particleanimlib.testing.manager;

import fr.skytale.particleanimlib.testing.ParticleAnimLibTest;
import fr.skytale.particleanimlib.trail.TrailTask;
import fr.skytale.particleanimlib.trail.attribute.TrailPreset;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrailSampleManager {

    public static final String NOT_IMPLEMENTED = "This preset does not exist. Select an existing trail or implement the required code in \"fr/skytale/particleanimlib/trail/attribute/TrailPreset.java\"";

    private static TrailSampleManager instance = null;

    public static TrailSampleManager getInstance() {
        if (instance == null) {
            instance = new TrailSampleManager();
        }
        return instance;
    }

    private final Map<TrailPreset, TrailTask> trailTasksByPreset;

    private TrailSampleManager() {
        trailTasksByPreset = new HashMap<>();
    }

    public TrailTask getTrailTask(String trailAnimationSampleName, Player player) {
        TrailPreset preset = TrailPreset.fromName(trailAnimationSampleName);
        TrailTask trailTask = trailTasksByPreset.get(preset);
        if (trailTask == null) {
            try {
                //Try to initialize task
                trailTask = preset.createBuilder(ParticleAnimLibTest.getInstance().getPlugin()).getTrail().getTrailTask();
                trailTasksByPreset.put(preset, trailTask);
            } catch (IllegalArgumentException e) {
                player.sendMessage(NOT_IMPLEMENTED);
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
