package fr.skytale.particleanimlib.animation.animation.text.preset;

import fr.skytale.particleanimlib.animation.animation.text.TextBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class RotatingTextKGEverSinceNewYorkPresetExecutor extends AAnimationPresetExecutor<TextBuilder> {

    public RotatingTextKGEverSinceNewYorkPresetExecutor() {
        super(TextBuilder.class);
    }

    @Override
    protected void apply(TextBuilder textBuilder, JavaPlugin plugin) {
        textBuilder.setRotation(new Vector(0, 0, 1), new Vector(0, 1, 0));
        textBuilder.setTicksDuration(100);
        textBuilder.setShowPeriod(new Constant<>(1));
        textBuilder.setString(new Constant<>("Coucou"));
        textBuilder.setFontSize(new Constant<>(10.0d));
        textBuilder.setFontFileName("KGEverSinceNewYork.ttf");
        textBuilder.setAlignCenter(true);
        textBuilder.setRotation(Orientation.EAST,new Vector(0, 1, 0), Math.PI / 120);
    }
}
