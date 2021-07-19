package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class ParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public ParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        parabolaBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null));
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setShowFrequency(3);
    }
}
