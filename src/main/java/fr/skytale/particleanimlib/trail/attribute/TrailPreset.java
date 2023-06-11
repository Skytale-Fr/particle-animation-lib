package fr.skytale.particleanimlib.trail.attribute;

import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import fr.skytale.particleanimlib.trail.preset.circle.*;
import fr.skytale.particleanimlib.trail.preset.CuboidTrailPresetExecutor;
import fr.skytale.particleanimlib.trail.preset.CirclePotionTrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public enum TrailPreset {
    CIRCLE_ORIENTED(new CircleOrientedTrailPresetExecutor()),
    CIRCLE_MOVING_ORIENTED(new CircleMovingOrientedTrailPresetExecutor()),
    CIRCLE_MOVING_UP(new CircleMovingUpTrailPresetExecutor()),
    CIRCLE_TARGETING_ENTITY(new CircleTargetingEntityTrailPresetExecutor()),
    CIRCLE_TARGETING_ENTITY_LOCATION(new CircleTargetingEntityLocationPresetExecutor()),
    CIRCLE_CLOUD(new CircleCloudTrailPresetExecutor()),
    POTION(new CirclePotionTrailPresetExecutor()),
    ROTATING_CUBOID(new CuboidTrailPresetExecutor())
    ;

    private final ATrailPresetExecutor presetExecutor;

    private final Class<? extends APresetInitializer>[] presetPrerequisitesClasses;

    @SafeVarargs
    TrailPreset(ATrailPresetExecutor presetExecutor, Class<? extends APresetInitializer>... presetPrerequisitesClasses) {
        this.presetExecutor = presetExecutor;
        this.presetPrerequisitesClasses = presetPrerequisitesClasses;
    }

    public static TrailPreset fromName(String name) {
        if (name == null) throw new NullPointerException("name should not be null");
        return TrailPreset.valueOf(name.trim().toUpperCase(Locale.ROOT));
    }

    public void apply(TrailBuilder builder, JavaPlugin plugin) {
        for (Class<? extends APresetInitializer> presetPrerequisiteClass : presetPrerequisitesClasses) {
            APresetInitializer.initialize(presetPrerequisiteClass, plugin);
        }
        presetExecutor.applyPreset(builder, plugin);
    }

    public TrailBuilder createBuilder(JavaPlugin plugin) {
        return presetExecutor.createBuilder(plugin);
    }
}
