package fr.skytale.particleanimlib.animation.animation.pyramid.preset;

import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class GrowingPyramidPresetExecutor extends AAnimationPresetExecutor<PyramidBuilder> {

    public GrowingPyramidPresetExecutor() {
        super(PyramidBuilder.class);
    }

    @Override
    protected void apply(PyramidBuilder pyramidBuilder) {
        pyramidBuilder.setDistanceBetweenParticles(new DoublePeriodicallyEvolvingVariable(0.2, 0.015, 2));
        pyramidBuilder.setDistanceToAnyBaseApex(new DoublePeriodicallyEvolvingVariable(1.0, 0.08, 2));
        pyramidBuilder.setFromCenterToApex(new VectorPeriodicallyEvolvingVariable(new Vector(0, -2.0, 0), new Vector(0, 0.2, 0), 2));
        pyramidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        pyramidBuilder.setNbBaseApex(new IntegerPeriodicallyEvolvingVariable(3, 1, 40));
        pyramidBuilder.setTicksDuration(200);
        pyramidBuilder.setShowFrequency(new Constant<>(1));
    }
}
