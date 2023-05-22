package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.animation.circle.preset.*;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidRotatingResizingPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidRotatingResizingWithInsideCollisionsPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.cuboid.preset.CuboidWithInsideCollisionsPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.epi.preset.SimpleEpiPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.helix.preset.DoubleHelixMovingUpDownPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.helix.preset.HelixSubAnimPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.helix.preset.HelixPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.image.preset.*;
import fr.skytale.particleanimlib.animation.animation.lightning.preset.LightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lightning.preset.LightningSubAnimLightningPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.lightning.preset.LightningSubAnimSpherePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.line.preset.*;
import fr.skytale.particleanimlib.animation.animation.nodes.preset.SimpleNodePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.obj.preset.*;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.ParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.RandomizedDirectionParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.parabola.preset.RandomizedSpeedAndDirectionParabolaPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.GrowingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.RotatingPolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.polygon.preset.SimplePolygonPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramid2PresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.GrowingPyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.pyramid.preset.SimplePyramidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.rose.preset.RotatingRoseInsideEpiPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.rose.preset.SimpleRosePresetExecutor;
import fr.skytale.particleanimlib.animation.animation.sphere.preset.*;
import fr.skytale.particleanimlib.animation.animation.text.preset.*;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.preset.RotatingTorusSolenoidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.preset.TorusSolenoidPresetExecutor;
import fr.skytale.particleanimlib.animation.animation.wave.preset.WavePresetExecutor;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import fr.skytale.particleanimlib.animation.parent.preset.APresetInitializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public enum AnimationPreset {
    //ATOM(new AtomPresetExecutor()),
    PA_0_01_FUMEE(new PA001FumeePresetExecutor()),
    PA_0_02_BRUME1(new PA002Brume1PresetExecutor()),
    PA_0_02_BRUME2(new PA002Brume2PresetExecutor()),
    PA_0_03_BRUME_EPAISSE1(new PA003BrumeEpaisse1PresetExecutor()),
    PA_0_03_BRUME_EPAISSE2(new PA003BrumeEpaisse2PresetExecutor()),
    PA_0_04_RITA_LONG(new PA004RitaLongPresetExecutor()),
    PA_0_04_RITA_COURT(new PA004RitaCourtPresetExecutor()),
    PA_0_05_FIL_OR(new PA005FilOrPresetExecutor()),

    PA_1_01_MAGIE_FUMEE_1(new PA101MagieFumee1PresetExecutor()),
    PA_1_01_MAGIE_FUMEE_3_0(new PA101MagieFumee3PresetExecutor()),
    PA_1_01_MAGIE_FUMEE_3_1(new PA101MagieFumee31PresetExecutor()),
    PA_1_01_MAGIE_FUMEE_3_2(new PA101MagieFumee32PresetExecutor()),

    PA_2_02_RITA_LISEUSE(new PA202LiseusePresetExecutor()),
    PA_2_03_ATTAQUE_CHARGE_1(new PA203Attaque1ChargePresetExecutor()),
    PA_2_03_ATTAQUE_CHARGE_2(new PA203Attaque2ChargePresetExecutor()),
    PA_2_03_ATTAQUE_CHARGE_3(new PA203Attaque3ChargePresetExecutor()),
    PA_2_03_ATTAQUE_CHARGE_4(new PA203Attaque4ChargePresetExecutor()),
    PA_2_05_MARQUE_MAGIQUE(new PA205MarqueMagiquePresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_1(new PA206ProtectionArcanique1PresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_2(new PA206ProtectionArcanique2PresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_3(new PA206ProtectionArcanique3PresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_4(new PA206ProtectionArcanique4PresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_4_1(new PA206ProtectionArcanique41PresetExecutor()),
    PA_2_06_PROTECTION_ARCANIQUE_4_2(new PA206ProtectionArcanique42PresetExecutor()),

    CIRCLE(new SimpleCirclePresetExecutor()),
    CIRCLE_HALF_GROWING(new GrowingHalfCirclePresetExecutor()),
    CIRCLE_MOVING_POINT(new MovingPointCirclePresetExecutor()),
    CIRCLE_ROTATING(new RotatingCirclePresetExecutor()),
    CIRCLE_ROTATING_WITH_INSIDE_COLLISIONS(new RotatingCircleWithInsideCollisionsPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING(new SubAnimationCircleRotatingPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA(new SubAnimationParabolaRotatingPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA2(new SubAnimationParabolaRotating2PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PARABOLA3(new SubAnimationParabolaRotating3PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING_PYRAMID(new SubAnimationCircleRotatingPyramidPresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING2(new SubAnimationCircleRotating2PresetExecutor()),
    CIRCLE_SUB_ANIM_ROTATING3(new SubAnimationCircleRotating3PresetExecutor()),
    CIRCLE_THEN_WAVE(new CircleWavePresetExecutor()),
    CIRCLE_THEN_WAVE_REVERSED(new CircleWaveReversedPresetExecutor()),
    CIRCLE_WITH_INSIDE_COLLISIONS(new SimpleCircleWithInsideCollisionsPresetExecutor()),
    CUBOID(new CuboidPresetExecutor()),
    CUBOID_ROTATING_RESIZING(new CuboidRotatingResizingPresetExecutor()),
    CUBOID_ROTATING_RESIZING_WITH_INNER_COLLISIONS(new CuboidRotatingResizingWithInsideCollisionsPresetExecutor()),
    CUBOID_WITH_INSIDE_COLLISIONS(new CuboidWithInsideCollisionsPresetExecutor()),
    EPI(new SimpleEpiPresetExecutor()),
    EXPLODING_LINES(new ExplodingLinesPresetExecutor()),
    HELICOPTER(new HelicopterPresetExecutor()),
    HELICOPTER_WITH_COLLISIONS(new HelicopterWithCollisionsPresetExecutor()),
    IMAGE_COUNTDOWN(new CountdownImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_MAGIC_CIRCLE(new MagicCircleImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_PIG_BOAT_BOW(new PigBoatBowImagePresetExecutor(), ImagePresetInitializer.class),
    IMAGE_SKYTALE(new SkytaleImagePresetExecutor(), ImagePresetInitializer.class),
    LIGHTNING(new LightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_LIGHTNING(new LightningSubAnimLightningPresetExecutor()),
    LIGHTNING_SUB_ANIM_SPHERE(new LightningSubAnimSpherePresetExecutor()),
    LINE(new SimpleLinePresetExecutor()),
    LINE_HANDS_OF_CLOCK(new LineHandsOfClockPresetExecutor()),
    LINE_ROTATING_ALONG_Y(new LineRotationYPresetExecutor()),
    LINE_WITH_COLLISIONS(new SimpleLineWithCollisionPresetExecutor()),
    LINE_WITH_POLYGONS(new LineWithPolygonsPresetExecutor()),
    NODE(new SimpleNodePresetExecutor()),
    OBJ_AL(new AlObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_CROWN(new CrownPresetExecutor(), ObjPresetInitializer.class),
    OBJ_CUBE(new CubeObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HAMMER(new HammerObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HUMANOID_QUAD(new HumanoidQuadObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_HUMANOID_TRI(new HumanoidTriObjPresetExecutor(), ObjPresetInitializer.class),
    OBJ_SHUTTLE(new ShuttleObjPresetExecutor(), ObjPresetInitializer.class),
    PARABOLA(new ParabolaPresetExecutor()),
    PARABOLA_RAND_DIRECTION(new RandomizedDirectionParabolaPresetExecutor()),
    PARABOLA_RAND_DIRECTION_AND_SPEED(new RandomizedSpeedAndDirectionParabolaPresetExecutor()),
    POLYGON(new SimplePolygonPresetExecutor()),
    POLYGON_GROWING(new GrowingPolygonPresetExecutor()),
    POLYGON_ROTATING(new RotatingPolygonPresetExecutor()),
    PYRAMID(new SimplePyramidPresetExecutor()),
    PYRAMID_GROWING(new GrowingPyramidPresetExecutor()),
    PYRAMID_GROWING2(new GrowingPyramid2PresetExecutor()),
    ROSE(new SimpleRosePresetExecutor()),
    ROSE_INSIDE_EPI_ROTATING(new RotatingRoseInsideEpiPresetExecutor()),
    SPHERE(new SpherePresetExecutor()),
    SPHERE_ELECTRIC(new ElectricExplodingSpherePresetExecutor()),
    SPHERE_ELECTRIC2(new ElectricSpherePresetExecutor()),
    SPHERE_HALF_GROWING(new ExplodingHalfSpherePresetExecutor()),
    SPHERE_PLASMA(new SpherePlasmaPresetExecutor()),
    SPHERE_PROPAGATION_BOTTOM_TO_TOP(new PropagatingUpSpherePresetExecutor()),
    SPHERE_PROPAGATION_TOP_TO_BOTTOM(new PropagatingDownSpherePresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON(new SphereSubAnimPolygonPresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON_PROPAGATION(new SphereSubAnimPolygonPropagatingUpPresetExecutor()),
    SPHERE_SUB_ANIM_POLYGON2(new SphereSubAnimPolygon2PresetExecutor()),
    SPHERE_SUB_ANIM_HELIX_PROPAGATION(new SphereSubAnimHelixPropagatingUpPresetExecutor()),
    SPHERE_SUB_ANIM_HELIX_PROPAGATION2(new SphereSubAnimHelixPropagatingUp2PresetExecutor()),
    SPHERE_SUB_ANIM_ROTATING_CIRCLES(new SphereSubAnimRotatingCirclesPresetExecutor()),
    SPHERE_WITH_INSIDE_COLLISIONS(new SphereWithInsideCollisionsPresetExecutor()),
    HELIX(new HelixPresetExecutor()),
    HELIX_MOVING_UP_DOWN(new DoubleHelixMovingUpDownPresetExecutor()),
    HELIX_SUB_ANIM_POLYGON(new HelixSubAnimPolygonPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK(new SimpleTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_CONTENT_EVOLVING(new ContentEvolvingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_DETAILSLEVEL_EVOLVING(new DetailsEvolvingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_FONTSIZE_EVOLVING(new FontSizeEvolvingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_RAINBOW(new RainbowTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_KGEVERSINCENEWYORK_ROTATING(new RotatingTextKGEverSinceNewYorkPresetExecutor()),
    TEXT_MINECRAFT(new SimpleTextMinecraftPresetExecutor()),
    TORUS_SOLENOID(new TorusSolenoidPresetExecutor()),
    TORUS_SOLENOID_ROTATING(new RotatingTorusSolenoidPresetExecutor()),
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

    public Class<? extends AAnimationBuilder<?, ?>> getBuilderClass() {
        return presetExecutor.getBuilderClass();
    }

    public AAnimationPresetExecutor<?> getPresetExecutor() {
        return presetExecutor;
    }

    public void apply(AAnimationBuilder<?, ?> builder, JavaPlugin plugin) {
        presetExecutor.checkCompatibility(builder);
        for (Class<? extends APresetInitializer> presetPrerequisiteClass : presetPrerequisitesClasses) {
            APresetInitializer.initialize(presetPrerequisiteClass, plugin);
        }

        presetExecutor.applyPreset(builder, plugin);
    }

    public AAnimationBuilder<?, ?> createBuilder(JavaPlugin plugin) {
        return presetExecutor.createBuilder(plugin);
    }
}
