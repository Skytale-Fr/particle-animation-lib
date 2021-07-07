package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SimpleCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SimpleCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circle) {
        circle.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circle.setNbPoints(20, true);
        circle.setRadius(4);
        circle.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circle.setTicksDuration(100);
        circle.setShowFrequency(new Constant<>(1));
    }
}
