package fr.skytale.particleanimlib.animation.animation.circuitpulse2d.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.circuitpulse2d.CircuitPulse2D;
import fr.skytale.particleanimlib.animation.animation.circuitpulse2d.CircuitPulse2DBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CircuitPulsePresetExecutor extends AAnimationPresetExecutor<CircuitPulse2DBuilder> {

    public CircuitPulsePresetExecutor() {
        super(CircuitPulse2DBuilder.class);
    }

    @Override
    protected void apply(CircuitPulse2DBuilder circuitPulse2DBuilder, JavaPlugin plugin) {
        // Nothing different from builder
    }
}
