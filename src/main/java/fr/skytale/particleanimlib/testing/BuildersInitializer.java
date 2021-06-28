package fr.skytale.particleanimlib.testing;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.attributes.AnimationSample;
import fr.skytale.particleanimlib.testing.samples.IPAnimSample;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BuildersInitializer {

    public static final String PACKAGE = "fr.skytale.particleanimlib.testing.samples";
    public static BuildersInitializer instance = null;

    public static BuildersInitializer getInstance() {
        if (instance == null) {
            instance = new BuildersInitializer();
        }
        return instance;
    }

    private final Map<AnimationSample, IPAnimSample> samples;

    private BuildersInitializer() {
        samples = getSamples();
    }

    public AAnimationBuilder<?> initBuilder(Player player, JavaPlugin plugin, AnimationSample animationSample) {
        IPAnimSample sample = samples.get(animationSample);
        if (sample == null) {
            player.sendMessage("Please implement the required code in ParticleAnimLibTest class");
            throw new NotImplementedException("Please implement the required code in ParticleAnimLibTest class");
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
        return samples.values().stream()
                .map(sample -> sample.getInitializedBuilder(player, plugin))
                .collect(Collectors.toList());
    }

    @NotNull
    private Map<AnimationSample, IPAnimSample> getSamples() {
        final Map<AnimationSample, IPAnimSample> samples;
        samples = new HashMap<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(PACKAGE).scan()) {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(IPAnimSample.class.getCanonicalName());
            widgetClasses.forEach(classInfo -> {
                Class<? extends IPAnimSample> clazz = classInfo.loadClass().asSubclass(IPAnimSample.class);
                try {
                    IPAnimSample particleAnimSample = clazz.newInstance();
                    samples.put(particleAnimSample.getType(), particleAnimSample);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new UnsupportedOperationException("Error listing migration classes", e);
                }
            });
        }
        return samples;
    }

}
