package fr.skytale.particleanimlib.testing.manager;

import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimationSampleManager {

    public static final String NOT_IMPLEMENTED = "This preset does not exist. Please implement the required code in \"fr/skytale/particleanimlib/animation/parent/preset/AnimationPreset.java\"";

    public static AnimationSampleManager instance = null;

    public static AnimationSampleManager getInstance() {
        if (instance == null) {
            instance = new AnimationSampleManager();
        }
        return instance;
    }

    private AnimationSampleManager() {
    }

    public AAnimationBuilder<?> initBuilder(Player player, APosition position, JavaPlugin plugin, String animationSampleName) {
        AnimationPreset animationPreset;
        try {
            animationPreset = AnimationPreset.fromName(animationSampleName);
        } catch (IllegalArgumentException e) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        AAnimationBuilder<?> builder = animationPreset.createBuilder();
        builder.setPosition(position);
        builder.setJavaPlugin(plugin);
        return builder;
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
        return Arrays.stream(AnimationPreset.values())
                .map(animationPreset -> initBuilder(player, APosition.fromEntity(player), plugin, animationPreset.name()))
                .collect(Collectors.toList());
    }

    public Set<String> getAnimationNames() {
        return Arrays.stream(AnimationPreset.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
