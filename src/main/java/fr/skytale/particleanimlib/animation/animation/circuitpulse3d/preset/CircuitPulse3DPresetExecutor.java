package fr.skytale.particleanimlib.animation.animation.circuitpulse3d.preset;

import fr.skytale.particleanimlib.animation.animation.circuitpulse3d.CircuitPulse3DBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CircuitPulse3DPresetExecutor extends AAnimationPresetExecutor<CircuitPulse3DBuilder> {

    public CircuitPulse3DPresetExecutor() {
        super(CircuitPulse3DBuilder.class);
    }

    @Override
    protected void apply(CircuitPulse3DBuilder circuitPulse3DBuilder, JavaPlugin plugin) {
        // Nothing different from builder
    }
}
