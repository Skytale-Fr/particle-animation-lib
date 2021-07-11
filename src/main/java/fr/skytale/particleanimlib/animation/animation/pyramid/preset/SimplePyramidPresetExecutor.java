package fr.skytale.particleanimlib.animation.animation.pyramid.preset;

import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SimplePyramidPresetExecutor extends AAnimationPresetExecutor<PyramidBuilder> {

    public SimplePyramidPresetExecutor() {
        super(PyramidBuilder.class);
    }

    @Override
    protected void apply(PyramidBuilder pyramidBuilder) {
        pyramidBuilder.setDistanceBetweenParticles(0.3);
        pyramidBuilder.setDistanceToAnyBaseApex(2.0);
        pyramidBuilder.setFromCenterToApex(new Vector(0, 4, 0));
        pyramidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        pyramidBuilder.setNbBaseApex(8);
        pyramidBuilder.setTicksDuration(200);
        pyramidBuilder.setShowFrequency(new Constant<>(1));
    }
}
