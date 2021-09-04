package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.animation.circle.preset.*;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidRotatingResizingPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.image.preset.*;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningSubAnimLightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lighting.preset.LightningSubAnimSpherePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.line.preset.LineRotationYPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.line.preset.LineWithPolygonsPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.line.preset.SimpleLinePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.obj.preset.*;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.ParabolaPlayerAimPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.ParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.RandomizeParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.RandomizedRotatingParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.GrowingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.RotatingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.SimplePolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramid2PresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.SimplePyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.sphere.preset.*;
import fr.skytale.particleanimlib.animation.animation.spiral.preset.SpiralADNPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.spiral.preset.SpiralPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.text.preset.*;
import fr.skytale.particleanimlib.animation.animation.wave.preset.WavePresetExecutor;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public enum AnimationPreset {
    //ATOM(new AtomPresetExecutor()),
    CIRCLE(new SimpleCirclePresetExecutor()),
    CIRCLE_HALF_GROWING(new GrowingHalfCirclePresetExecutor()),
    CIRCLE_ROTATING(new RotatingCirclePresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING(new SubAnimationCircleRotatingPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING2(new SubAnimationCircleRotating2PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING3(new SubAnimationCircleRotating3PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA(new SubAnimationParabolaRotatingPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA2(new SubAnimationParabolaRotating2PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA3(new SubAnimationParabolaRotating3PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PYRAMID(new SubAnimationCircleRotatingPyramidPresetExecutor()),
    CIRCLE_THEN_WAVE(new CircleWavePresetExecutor()),
    CIRCLE_THEN_WAVE_REVERSED(new CircleWaveReversedPresetExecutor()),
    CUBOID(new CuboidPresetExecutor()),
    CUBOID_ROTATING_RESIZING(new CuboidRotatingResizingPresetExecutor()),
    IMAGE_SKYTALE(new SkytaleImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_MAGIC_CIRCLE(new MagicCircleImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_COUNTDOWN(new CountdownImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_PIG_BOAT_BOW(new PigBoatBowImagePresetExecutor(), ImagePresetInitializer.class),
    LIGHTNING(new LightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_LIGHTNING(new LightningSubAnimLightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_SPHERE(new LightningSubAnimSpherePresetExecutor()),
    OBJ_CROWN(new CrownPresetExecutor(), ObjPresetInitializer.class),
    OBJ_CUBE(new CubeObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HAMMER(new HammerObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_AL(new AlObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HUMANOID_QUAD(new HumanoidQuadObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HUMANOID_TRI(new HumanoidTriObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_SHUTTLE(new ShuttleObjPresetExecutor(), ObjPresetInitializer.class),
    PARABOLA_PLAYER_AIM(new ParabolaPlayerAimPresetExecutor()),
    PARABOLA_RAND_ROTATION(new RandomizeParabolaPresetExecutor()),
    PARABOLA_RAND_DIRECTION_AND_ROTATION(new RandomizedRotatingParabolaPresetExecutor()),
    PARABOLA(new ParabolaPresetExecutor()),
    POLYGON(new SimplePolygonPresetExecutor()),
    POLYGON_GROWING(new GrowingPolygonPresetExecutor()),
    POLYGON_ROTATING(new RotatingPolygonPresetExecutor()),
    PYRAMID(new SimplePyramidPresetExecutor()),
    PYRAMID_GROWING(new GrowingPyramidPresetExecutor()),
    PYRAMID_GROWING2(new GrowingPyramid2PresetExecutor()),
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
    WAVE(new WavePresetExecutor()),
    LINE(new SimpleLinePresetExecutor()),
    LINE_WITH_POLYGONS(new LineWithPolygonsPresetExecutor()),
    LINE_ROTATING_ALONG_Y(new LineRotationYPresetExecutor()),
    HELICOPTER(new HelicopterPresetExecutor()),
    SPHERE_PLASMA(new SpherePlasmaPresetExecutor()),
    TEXT_MINECRAFT(new SimpleTextMinecraftPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK(new SimpleTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_FONTSIZE_EVOLVING(new FontSizeEvolvingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_CONTENT_EVOLVING(new ContentEvolvingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_ROTATING(new RotatingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_RAINBOW(new RainbowTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_DETAILSLEVEL_EVOLVING(new DetailsEvolvingTextKGEverSinceNewYorkPresetExecutor());


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

    public void apply(AAnimationBuilder<?> builder, JavaPlugin plugin) {
        presetExecutor.checkCompatibility(builder);
        for (Class<? extends APresetInitializer> presetPrerequisiteClass : presetPrerequisitesClasses) {
            APresetInitializer.initialize(presetPrerequisiteClass, plugin);
        }

        presetExecutor.applyPreset(builder, plugin);
    }

    public AAnimationBuilder<?> createBuilder(JavaPlugin plugin) {
        return presetExecutor.createBuilder(plugin);
    }
}
