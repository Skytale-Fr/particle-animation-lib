package fr.skytale.particleanimlib.animation.animation.spiral.preset;

import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SpiralPresetExecutor extends AAnimationPresetExecutor<SpiralBuilder> {

    public SpiralPresetExecutor() {
        super(SpiralBuilder.class);
    }

    @Override
    protected void apply(SpiralBuilder spiralBuilder) {
        spiralBuilder.setRadius(2);
        spiralBuilder.setNbSpiral(new IntegerPeriodicallyEvolvingVariable(1, 1, 30));
        spiralBuilder.setAngleBetweenEachPoint(Math.PI / 24);
        spiralBuilder.setNbTrailingParticles(3);
        spiralBuilder.setCentralParticle(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null));
        spiralBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        spiralBuilder.setDirection(AnimationDirection.fromMoveVector(new VectorPeriodicallyEvolvingVariable(new Vector(0, 0.1, 0), new Vector(0, 0.01, 0), 0)));
        spiralBuilder.setTicksDuration(200);
        spiralBuilder.setShowFrequency(2);
    }
}
