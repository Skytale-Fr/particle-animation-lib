package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;

public class RandomizeParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public RandomizeParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        // randomize direction callback with previous value
        // rotating circle preset
        AnimationPreset.PARABOLA.apply(parabolaBuilder, plugin);
        parabolaBuilder.setRotation(
                new CallbackWithPreviousValueVariable<>(
                        new Vector(1, 1, 1),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(
                                Math.random() / 20, Math.random() / 20, Math.random() / 20)).normalize()
                ),
                Math.PI / 10
        );
        parabolaBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 0, 0)));
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setShowPeriod(new Constant<>(3));
    }
}
