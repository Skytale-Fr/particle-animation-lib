package fr.skytale.particleanimlib.testing.manager;

import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.OnEntityAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class AnimationSampleManager {

    public static final String NOT_IMPLEMENTED = "This preset does not exist. Please implement the required code in \"fr/skytale/particleanimlib/animation/parent/preset/AnimationPreset.java\"";

    public static AnimationSampleManager instance = null;

    private AnimationSampleManager() {
    }

    public static AnimationSampleManager getInstance() {
        if (instance == null) {
            instance = new AnimationSampleManager();
        }
        return instance;
    }

    public AAnimationBuilder<?, ?> initBuilder(Player player, AAnimationPosition position, JavaPlugin plugin, String animationSampleName) {
        AnimationPreset animationPreset;
        try {
            animationPreset = AnimationPreset.fromName(animationSampleName);
        } catch (IllegalArgumentException e) {
            player.sendMessage(NOT_IMPLEMENTED);
            throw new NotImplementedException(NOT_IMPLEMENTED);
        }
        AAnimationBuilder<?, ?> builder = animationPreset.getPresetExecutor().createEmptyBuilder();
        builder.setPosition(position);
        animationPreset.apply(builder, plugin);
        return builder;
    }

    public void showChainedBuilders(Player player, JavaPlugin plugin) {
        List<AnimationPreset> presets = new ArrayList<AnimationPreset>(Arrays.asList(AnimationPreset.values()));
        presets.sort(Comparator.comparingInt(preset -> preset.name().length()));

        AAnimationBuilder<?, ?> prevBuilder = null;
        AAnimationBuilder<?, ?> firstBuilder = null;
        String firstBuilderStr = null;
        for (AnimationPreset preset : presets) {
            AAnimationBuilder<?, ?> currentBuilder = initBuilder(player, new OnEntityAnimationPosition(player), plugin, preset.name());
            String animationStr = String.format("Showing animation preset \"%s\"", preset.name());
            if (prevBuilder != null) {
                prevBuilder.addAnimationEndedCallback((anim) -> {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        player.sendMessage(animationStr);
                        currentBuilder.getAnimation().show();
                    }, 40);
                });
            } else {
                firstBuilder = currentBuilder;
                firstBuilderStr = animationStr;
            }
            prevBuilder = currentBuilder;

        }
        assert firstBuilderStr != null;
        assert firstBuilder != null;
        player.sendMessage(firstBuilderStr);
        firstBuilder.getAnimation().show();
    }

    public Set<String> getAnimationNames() {
        return Arrays.stream(AnimationPreset.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
