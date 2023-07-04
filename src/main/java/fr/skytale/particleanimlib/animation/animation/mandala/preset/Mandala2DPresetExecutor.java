package fr.skytale.particleanimlib.animation.animation.mandala.preset;

import fr.skytale.particleanimlib.animation.animation.mandala.Mandala2DBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Mandala2DPresetExecutor extends AAnimationPresetExecutor<Mandala2DBuilder> {

    public Mandala2DPresetExecutor() {
        super(Mandala2DBuilder.class);
    }

    @Override
    protected void apply(Mandala2DBuilder mandala2DBuilder, JavaPlugin plugin) {
        // Nothing different from builder
    }
}
