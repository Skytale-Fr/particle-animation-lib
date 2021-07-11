package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class GrowingHalfCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public GrowingHalfCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder) {
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(20, false);
        circleBuilder.setAngleBetweenEachPoint(Math.PI / 20);
        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(1.0, 0.2, 1));
        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowFrequency(new Constant<>(1));
    }
}
