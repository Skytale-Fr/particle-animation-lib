package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.animation.circle.preset.GrowingHalfCirclePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.circle.preset.RotatingCirclePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.circle.preset.SimpleCirclePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.SmallCuboidRotatingPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.image.preset.ImagePresetInitializer;
import fr.skytale.particleanimlib.animation.animation.image.preset.SkytaleImagePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.GrowingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.RotatingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.SimplePolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.sphere.preset.*;
import fr.skytale.particleanimlib.animation.animation.spiral.preset.SpiralPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.wave.preset.WavePresetExecutor;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;

import java.util.Locale;

public enum AnimationPreset {
    //ATOM(new AtomPresetExecutor()),
    CIRCLE(new SimpleCirclePresetExecutor()),
    CIRCLE_HALF_GROWING(new GrowingHalfCirclePresetExecutor()),
    CIRCLE_ROTATING(new RotatingCirclePresetExecutor()),
    CUBOID_ROTATING(new SmallCuboidRotatingPresetExecutor()),
    IMAGE_SKYTALE(new SkytaleImagePresetExecutor(), ImagePresetInitializer.class),
    POLYGON(new SimplePolygonPresetExecutor()),
    POLYGON_GROWING(new GrowingPolygonPresetExecutor()),
    POLYGON_ROTATING(new RotatingPolygonPresetExecutor()),
    PYRAMID_GROWING(new GrowingPyramidPresetExecutor()),
    SPHERE(new SpherePresetExecutor()),
    SPHERE_PROPAGATION_BOTTOM_TO_TOP(new PropagatingUpSpherePresetExecutor()),
    SPHERE_PROPAGATION_TOP_TO_BOTTOM(new PropagatingDownSpherePresetExecutor()),
    SPHERE_ELECTRIC(new ElectricExplodingSpherePresetExecutor()),
    SPHERE_HALF_GROWING(new ExplodingHalfSpherePresetExecutor()),
    SPIRAL(new SpiralPresetExecutor()),
    WAVE(new WavePresetExecutor())
    ;

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
