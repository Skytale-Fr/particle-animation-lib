package fr.skytale.particleanimlib.animation.animation.circuitpulse2d.preset;

import fr.skytale.particleanimlib.animation.animation.circuitpulse2d.CircuitPulse2DBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CircuitPulse2DPresetExecutor extends AAnimationPresetExecutor<CircuitPulse2DBuilder> {

    public CircuitPulse2DPresetExecutor() {
        super(CircuitPulse2DBuilder.class);
    }

    @Override
    protected void apply(CircuitPulse2DBuilder circuitPulse2DBuilder, JavaPlugin plugin) {
        // Nothing different from builder
    }
}
