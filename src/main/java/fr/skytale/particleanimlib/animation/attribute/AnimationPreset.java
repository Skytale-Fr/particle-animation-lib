package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.animation.circle.preset.*;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidRotatingResizingPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.image.preset.*;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningSubAnimLightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningSubAnimSpherePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.GrowingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.RotatingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.SimplePolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.SimplePyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.sphere.preset.*;
import fr.skytale.particleanimlib.animation.animation.spiral.preset.SpiralADNPresetExecutor;
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
    CIRCLE_SUB_ANIM_ROTATING(new SubAnimationCircleRotatingPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING2(new SubAnimationCircleRotating2PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PYRAMID(new SubAnimationCircleRotatingPyramidPresetExecutor()),
    CUBOID(new CuboidPresetExecutor()),
    CUBOID_ROTATING_RESIZING(new CuboidRotatingResizingPresetExecutor()),
    IMAGE_SKYTALE(new SkytaleImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_MAGIC_CIRCLE(new MagicCircleImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_COUNTDOWN(new CountdownImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_PIG_BOAT_BOW(new PigBoatBowImagePresetExecutor(), ImagePresetInitializer.class),
    LIGHTNING(new LightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_LIGHTNING(new LightningSubAnimLightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_SPHERE(new LightningSubAnimSpherePresetExecutor()),
    POLYGON(new SimplePolygonPresetExecutor()),
    POLYGON_GROWING(new GrowingPolygonPresetExecutor()),
    POLYGON_ROTATING(new RotatingPolygonPresetExecutor()),
    PYRAMID(new SimplePyramidPresetExecutor()),
    PYRAMID_GROWING(new GrowingPyramidPresetExecutor()),
    SPHERE(new SpherePresetExecutor()),
    SPHERE_PROPAGATION_BOTTOM_TO_TOP(new PropagatingUpSpherePresetExecutor()),
    SPHERE_PROPAGATION_TOP_TO_BOTTOM(new PropagatingDownSpherePresetExecutor()),
    SPHERE_ELECTRIC(new ElectricExplodingSpherePresetExecutor()),
    SPHERE_HALF_GROWING(new ExplodingHalfSpherePresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON(new SphereSubAnimPolygonPresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON2(new SphereSubAnimPolygon2PresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON_PROPAGATION(new SphereSubAnimPolygonPropagatingUpPresetExecutor()),
    SPHERE_SUB_ANIM_SPIRAL_PROPAGATION(new SphereSubAnimSpiralPropagatingUpPresetExecutor()),
    SPIRAL(new SpiralPresetExecutor()),
    SPIRAL_ADN(new SpiralADNPresetExecutor()),
    WAVE(new WavePresetExecutor());

    private final AAnimationPresetExecutor<?> presetExecutor;

    private final Class<? extends APresetInitializer>[] presetPrerequisitesClasses;

    @SafeVarargs
    AnimationPreset(AAnimationPresetExecutor<?> presetExecutor, Class<? extends APresetInitializer>... presetPrerequisitesClasses) {
        this.presetExecutor = presetExecutor;
        this.presetPrerequisitesClasses = presetPrerequisitesClasses;
    }

    public static AnimationPreset fromName(String name) {
        if (name == null) throw new NullPointerException("name should not be null");
        return AnimationPreset.valueOf(name.trim().toUpperCase(Locale.ROOT));
    }

    public Class<? extends AAnimationBuilder<?>> getBuilderClass() {
        return presetExecutor.getBuilderClass();
    }

    public AAnimationPresetExecutor<?> getPresetExecutor() {
        return presetExecutor;
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
}
