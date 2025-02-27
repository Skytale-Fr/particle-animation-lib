package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class RandomizedDirectionParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public RandomizedDirectionParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        AnimationPreset.PARABOLA.apply(parabolaBuilder, plugin);
        parabolaBuilder.setDirection(
                new CallbackVariable<>( iterationCount -> new Vector(
                                Math.random() - 0.30,
                                Math.random() - 0.30,
                                Math.random() - 0.30
                        ))
        );
    }
}
