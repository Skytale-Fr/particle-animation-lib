package fr.skytale.particleanimlib.animation.animation.pyramid.preset;

import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class GrowingPyramid2PresetExecutor extends AAnimationPresetExecutor<PyramidBuilder> {

    public GrowingPyramid2PresetExecutor() {
        super(PyramidBuilder.class);
    }

    @Override
    protected void apply(PyramidBuilder pyramidBuilder, JavaPlugin plugin) {
        pyramidBuilder.applyPreset(AnimationPreset.PYRAMID_GROWING, plugin);
        pyramidBuilder.setDistanceToAnyBaseApex(5);
        pyramidBuilder.setFromCenterToApex(new VectorPeriodicallyEvolvingVariable(new Vector(0, 0, 0), new Vector(0, 0.3, 0), 2));
        pyramidBuilder.setNbBaseApex(10);
        pyramidBuilder.setTicksDuration(200);
        pyramidBuilder.setShowPeriod(new Constant<>(2));
    }
}
