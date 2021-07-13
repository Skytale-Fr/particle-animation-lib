package fr.skytale.particleanimlib.animation.animation.polygon.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class GrowingPolygonPresetExecutor extends AAnimationPresetExecutor<PolygonBuilder> {

    public GrowingPolygonPresetExecutor() {
        super(PolygonBuilder.class);
    }

    @Override
    protected void apply(PolygonBuilder polygonBuilder, JavaPlugin plugin) {
        AnimationPreset.POLYGON.apply(polygonBuilder, plugin);
        polygonBuilder.setTicksDuration(400);
        polygonBuilder.setNbVertices(new IntegerPeriodicallyEvolvingVariable(3, 1, 40));
    }
}
