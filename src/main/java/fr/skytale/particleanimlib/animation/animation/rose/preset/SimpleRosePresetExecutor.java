package fr.skytale.particleanimlib.animation.animation.rose.preset;

import fr.skytale.particleanimlib.animation.animation.rose.RoseBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SimpleRosePresetExecutor extends AAnimationPresetExecutor<RoseBuilder> {

    public SimpleRosePresetExecutor() {
        super(RoseBuilder.class);
    }

    @Override
    protected void apply(RoseBuilder roseBuilder, JavaPlugin plugin) {
        roseBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        roseBuilder.setNbPoints(200, true);
        roseBuilder.setRadius(10);
        roseBuilder.setRoseModifier(new CallbackWithPreviousValueVariable<>(-10d, (iterationCount, previousValue) -> {
            return iterationCount%20 == 0 ? previousValue + 0.2 : previousValue;
        }));
        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        roseBuilder.setTicksDuration(2000);
        roseBuilder.setShowPeriod(new Constant<>(1));
    }
}
