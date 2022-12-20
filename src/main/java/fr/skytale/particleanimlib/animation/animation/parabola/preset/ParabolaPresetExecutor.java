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
        parabolaBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 0, 0)));
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setShowPeriod(3);
        parabolaBuilder.setBulletShootPeriod(10);
    }
}
