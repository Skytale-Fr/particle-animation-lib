package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class RepetitiveShrinkingSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public RepetitiveShrinkingSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        double startRadius = 20d;
        double endRadius = 4d;
        int tickDuration = 20 * 10;

        SphereBuilder originalAnimationBuilder = new SphereBuilder();
        originalAnimationBuilder.setPosition(sphereBuilder.getPosition());
        originalAnimationBuilder.setJavaPlugin(plugin);
        originalAnimationBuilder.setRotation(
                new VectorPeriodicallyEvolvingVariable(
                        new Vector(0, 1, 0),
                        new Vector(-0.01, -0.01, 0.01)),
                Math.PI / 12);

        originalAnimationBuilder.setNbPoints(5);
        originalAnimationBuilder.setRadius(startRadius);
        originalAnimationBuilder.setRadius(
                new DoublePeriodicallyEvolvingVariable(
                        startRadius,
                        (endRadius - startRadius) / tickDuration));

        originalAnimationBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.01f, new Vector(0.3, 0.3, 0.3), (ParticleData) null));

        originalAnimationBuilder.setSphereType(Sphere.Type.FULL);
        originalAnimationBuilder.setTicksDuration(tickDuration);
        originalAnimationBuilder.setShowPeriod(0);

        // Repetition
        sphereBuilder.setRadius(0.1);
        sphereBuilder.setNbPoints(1);
        sphereBuilder.setTicksDuration(20 * 2);
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FOOTSTEP));

        //3rd repetition
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimationBuilder.getAnimation().show();
        });
        Sphere circle_3 = sphereBuilder.getAnimation();

        //2nd repetition
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimationBuilder.getAnimation().show();
            circle_3.show();
        });
        Sphere circle_2 = sphereBuilder.getAnimation();

        //1rst repetition
        sphereBuilder.setTicksDuration(1);
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimationBuilder.getAnimation().show();
            circle_2.show();
        });
    }
}
