package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SkytaleImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public SkytaleImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder, JavaPlugin plugin) {
        imageBuilder.setRotation(
                new Vector(0, 0, 1), new Vector(0, -1, 0),
                new Vector(0, 1, 0), Math.PI / 20f);
        imageBuilder.setImageFileName("skytale.png");
        imageBuilder.setTicksDuration(40);
        imageBuilder.setShowPeriod(2);
    }
}
