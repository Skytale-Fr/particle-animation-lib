package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class GrowingHalfCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public GrowingHalfCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circle) {
        circle.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circle.setNbPoints(20, false);
        circle.setAngleBetweenEachPoint(Math.PI / 20);
        circle.setRadius(new DoublePeriodicallyEvolvingVariable(1.0, 0.2, 1));
        circle.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circle.setTicksDuration(100);
        circle.setShowFrequency(new Constant<>(1));
    }
}
