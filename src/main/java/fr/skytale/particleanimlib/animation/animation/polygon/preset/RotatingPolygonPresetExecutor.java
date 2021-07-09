package fr.skytale.particleanimlib.animation.animation.polygon.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

public class RotatingPolygonPresetExecutor extends AAnimationPresetExecutor<PolygonBuilder> {

    public RotatingPolygonPresetExecutor() {
        super(PolygonBuilder.class);
    }

    @Override
    protected void apply(PolygonBuilder polygonBuilder) {
        AnimationPreset.POLYGON_GROWING.apply(polygonBuilder);
        polygonBuilder.setTicksDuration(400);
        polygonBuilder.setRotation(
                new CallbackWithPreviousValueVariable<Vector>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
                ),
                Math.PI / 10
        );
    }
}
