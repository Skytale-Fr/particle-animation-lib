package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class ParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public ParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setShowPeriod(3);
        parabolaBuilder.setBulletShootPeriod(10);
    }
}
