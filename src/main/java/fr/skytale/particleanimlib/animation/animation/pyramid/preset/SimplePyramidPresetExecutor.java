package fr.skytale.particleanimlib.animation.animation.pyramid.preset;

import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SimplePyramidPresetExecutor extends AAnimationPresetExecutor<PyramidBuilder> {

    public SimplePyramidPresetExecutor() {
        super(PyramidBuilder.class);
    }

    @Override
    protected void apply(PyramidBuilder pyramidBuilder, JavaPlugin plugin) {
        pyramidBuilder.setDistanceBetweenParticles(0.3);
        pyramidBuilder.setDistanceToAnyBaseApex(2.0);
        pyramidBuilder.setFromCenterToApex(new Vector(0, 4, 0));
        pyramidBuilder.setNbBaseApex(8);
        pyramidBuilder.setTicksDuration(200);
        pyramidBuilder.setShowPeriod(new Constant<>(1));
    }
}
