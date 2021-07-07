package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.animation.cuboid.preset.SmallCuboidRotatingPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.image.preset.ImagePresetInitializer;
import fr.skytale.particleanimlib.animation.animation.image.preset.SkytaleImagePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.wave.preset.WavePresetExecutor;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;

import java.util.Locale;

public enum AnimationPreset {
    CUBOID_ROTATING(new SmallCuboidRotatingPresetExecutor()),
    IMAGE_SKYTALE(new SkytaleImagePresetExecutor(), ImagePresetInitializer.class),
    WAVE(new WavePresetExecutor());

    private final AAnimationPresetExecutor<?> presetExecutor;

    private final Class<? extends APresetInitializer>[] presetPrerequisitesClasses;

    @SafeVarargs
    AnimationPreset(AAnimationPresetExecutor<?> presetExecutor, Class<? extends APresetInitializer>... presetPrerequisitesClasses) {
        this.presetExecutor = presetExecutor;
        this.presetPrerequisitesClasses = presetPrerequisitesClasses;
    }

    public Class<? extends AAnimationBuilder<?>> getBuilderClass() {
        return presetExecutor.getBuilderClass();
    }

    public void apply(AAnimationBuilder<?> builder) {
        presetExecutor.checkCompatibility(builder);
        for (Class<? extends APresetInitializer> presetPrerequisiteClass : presetPrerequisitesClasses) {
            APresetInitializer.initialize(presetPrerequisiteClass);
        }

        presetExecutor.applyPreset(builder);
    }

    public AAnimationBuilder<?> createBuilder() {

        return presetExecutor.createBuilder();
    }

    public static AnimationPreset fromName(String name) {
        if (name == null) throw new NullPointerException("name should not be null");
        return AnimationPreset.valueOf(name.trim().toUpperCase(Locale.ROOT));
    }
}
