package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.samples.IPAnimSample;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class AnimationManager {

    public static final String PACKAGE = "fr.skytale.particleanimlib.testing.samples";
    public static final String NOT_IMPLEMENTED = "Please implement the required code in " + PACKAGE + " package";
    public static AnimationManager instance = null;

    public static AnimationManager getInstance() {
        if (instance == null) {
            instance = new AnimationManager();
        }
        return instance;
    }

    private final Map<String, IPAnimSample> samplesByName;

    private AnimationManager() {
        samplesByName = getSamples();
    }

    public AAnimationBuilder<?> initBuilder(Player player, JavaPlugin plugin, String animationSampleName) {
        IPAnimSample sample = samplesByName.get(animationSampleName);
        if (sample == null) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        return sample.getInitializedBuilder(player, plugin);
    }

    public AAnimationBuilder<?> getChainedBuilders(Player player, JavaPlugin plugin) {
        List<AAnimationBuilder<?>> allBuilders = getAllAnimationBuilders(player, plugin);
        AAnimationBuilder<?> prevBuilder = null;
        AAnimationBuilder<?> firstBuilder = null;
        for (int i = allBuilders.size() - 1; i >= 0; --i) {
            AAnimationBuilder<?> currentBuilder = allBuilders.get(i);
            if (prevBuilder != null) {
                prevBuilder.setCallback((anim) -> currentBuilder.getAnimation().show());
            } else {
                firstBuilder = currentBuilder;
            }
            prevBuilder = currentBuilder;
        }
        return firstBuilder;
    }

    private List<AAnimationBuilder<?>> getAllAnimationBuilders(Player player, JavaPlugin plugin) {
        return samplesByName.values().stream()
                .map(sample -> sample.getInitializedBuilder(player, plugin))
                .collect(Collectors.toList());
    }

    @NotNull
    private Map<String, IPAnimSample> getSamples() {
        final Map<String, IPAnimSample> samples;
        samples = new HashMap<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(PACKAGE).scan()) {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(IPAnimSample.class.getCanonicalName());
            widgetClasses.forEach(classInfo -> {
                Class<? extends IPAnimSample> clazz = classInfo.loadClass().asSubclass(IPAnimSample.class);
                try {
                    IPAnimSample particleAnimSample = clazz.newInstance();
                    samples.put(particleAnimSample.getName().toLowerCase(Locale.ROOT), particleAnimSample);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new UnsupportedOperationException("Error listing animation sample classes", e);
                }
            });
        }
        return samples;
    }

    public Set<String> getAnimationNames() {
        return samplesByName.keySet();
    }
}
