package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

public class RotatingCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public RotatingCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder) {
        AnimationPreset.CIRCLE.apply(circleBuilder);
        circleBuilder.setTicksDuration(400);
        circleBuilder.setRotation(
                new CallbackWithPreviousValueVariable<Vector>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
                ),
                Math.PI / 10
        );
    }
}
