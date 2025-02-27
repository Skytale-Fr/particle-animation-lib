package fr.skytale.particleanimlib.animation.animation.wave.preset;

import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WavePresetExecutor extends AAnimationPresetExecutor<WaveBuilder> {

    public WavePresetExecutor() {
        super(WaveBuilder.class);
    }

    @Override
    protected void apply(WaveBuilder waveBuilder, JavaPlugin plugin) {
        waveBuilder.setTicksDuration(400);
        waveBuilder.setShowPeriod(new Constant<>(0));
        waveBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        waveBuilder.setNbPoints(new Constant<>(50), true);
        waveBuilder.setRadiusMax(20);
        waveBuilder.setRadiusStart(1);
        waveBuilder.setRadiusStep(new CallbackVariable<>(iterationCount -> 0.3 + Math.sin(iterationCount) / 4));
    }
}
