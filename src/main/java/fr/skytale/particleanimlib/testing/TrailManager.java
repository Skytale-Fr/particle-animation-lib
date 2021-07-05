package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.testing.trailsamples.IPTrailAnimSample;
import fr.skytale.particleanimlib.trail.TrailTask;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TrailManager {

    public static final String PACKAGE = "fr.skytale.particleanimlib.testing.trailsamples";
    public static final String NOT_IMPLEMENTED = "Please implement the required code in " + PACKAGE + " package";
    public static TrailManager instance = null;

    public static TrailManager getInstance() {
        if (instance == null) {
            instance = new TrailManager();
        }
        return instance;
    }

    private final Map<String, TrailTask> trailsByName;

    private TrailManager() {
        trailsByName = getSamples();
    }

    public void enableTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = trailsByName.get(trailAnimationSampleName);
        if (trailTask == null) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        UUID playerUUID = player.getUniqueId();
        if (!trailTask.containsPlayer(playerUUID)) {
            trailTask.addPlayer(playerUUID);
        }

    }

    public void disableTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = trailsByName.get(trailAnimationSampleName);
        if (trailTask == null) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        UUID playerUUID = player.getUniqueId();
        if (trailTask.containsPlayer(playerUUID)) {
            trailTask.removePlayer(playerUUID);
        }
    }

    public boolean toggleTrail(Player player, String trailAnimationSampleName) {
        TrailTask trailTask = trailsByName.get(trailAnimationSampleName);
        if (trailTask == null) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        UUID playerUUID = player.getUniqueId();
        if (trailTask.containsPlayer(playerUUID)) {
            trailTask.removePlayer(playerUUID);
            return false;
        } else {
            trailTask.addPlayer(playerUUID);
            return true;
        }
    }

    @NotNull
    private Map<String, TrailTask> getSamples() {
        final Map<String, TrailTask> samples;
        samples = new HashMap<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(PACKAGE).scan()) {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(IPTrailAnimSample.class.getCanonicalName());
            widgetClasses.forEach(classInfo -> {
                Class<? extends IPTrailAnimSample> clazz = classInfo.loadClass().asSubclass(IPTrailAnimSample.class);
                try {
                    IPTrailAnimSample particleAnimSample = clazz.newInstance();
                    samples.put(particleAnimSample.getName().toLowerCase(Locale.ROOT), particleAnimSample.getTrailTask(ParticleAnimLibTest.getInstance().getPlugin()));
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new UnsupportedOperationException("Error listing trail animation sample classes", e);
                }
            });
        }
        return samples;
    }

    public Set<String> getTrailNames() {
        return trailsByName.keySet();
    }
}
