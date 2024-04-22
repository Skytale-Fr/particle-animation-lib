package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomizedSpeedAndDirectionParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public RandomizedSpeedAndDirectionParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        AnimationPreset.PARABOLA_RAND_DIRECTION.apply(parabolaBuilder, plugin);
        parabolaBuilder.setSpeed(new CallbackVariable<>(iterationCount -> Math.sin((2 + iterationCount) * 3d)));
    }
}
