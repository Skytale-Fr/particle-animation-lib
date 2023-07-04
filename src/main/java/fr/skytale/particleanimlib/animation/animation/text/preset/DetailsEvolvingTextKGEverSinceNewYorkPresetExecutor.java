package fr.skytale.particleanimlib.animation.animation.text.preset;

import fr.skytale.particleanimlib.animation.animation.text.TextBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class DetailsEvolvingTextKGEverSinceNewYorkPresetExecutor extends AAnimationPresetExecutor<TextBuilder> {

    public DetailsEvolvingTextKGEverSinceNewYorkPresetExecutor() {
        super(TextBuilder.class);
    }

    @Override
    protected void apply(TextBuilder textBuilder, JavaPlugin plugin) {
        textBuilder.setRotation(new Vector(0, 0, 1), new Vector(0, 1, 0));
        textBuilder.setTicksDuration(100);
        textBuilder.setShowPeriod(new Constant<>(1));
        textBuilder.setText(ChatColor.RED + "Coucou");
        textBuilder.setFontSize(10.0d);
        textBuilder.setFontFileName("KGEverSinceNewYork.ttf");
        textBuilder.setDetailsLevel(new CallbackWithPreviousValueVariable<>(5.0d, (iterationCount, previousValue) -> {
            return Math.max(0.2d, previousValue - 0.08d);
        }));
    }
}
