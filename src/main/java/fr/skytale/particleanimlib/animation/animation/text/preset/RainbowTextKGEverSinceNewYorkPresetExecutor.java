package fr.skytale.particleanimlib.animation.animation.text.preset;

import fr.skytale.particleanimlib.animation.animation.text.TextBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class RainbowTextKGEverSinceNewYorkPresetExecutor extends AAnimationPresetExecutor<TextBuilder> {

    private static final String AVAILABLE_COLORS = "0123456789abcdef";
    private static final String TEXT_TO_DISPLAY = "Skytale";

    public RainbowTextKGEverSinceNewYorkPresetExecutor() {
        super(TextBuilder.class);
    }

    @Override
    protected void apply(TextBuilder textBuilder, JavaPlugin plugin) {
        textBuilder.setRotation(new Vector(0, 0, 1), new Vector(0, 1, 0));
        textBuilder.setTicksDuration(100);
        textBuilder.setShowPeriod(new Constant<>(4));
        textBuilder.setText(new CallbackWithPreviousValueVariable<>("Skytale", (iterationCount, previousValue) -> {
            if (iterationCount % 3 != 0) return previousValue;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < TEXT_TO_DISPLAY.length(); i++) {
                char colorChar = AVAILABLE_COLORS.charAt((int) Math.round(
                        Math.random() * (AVAILABLE_COLORS.length() - 1)));
                ChatColor color = ChatColor.getByChar(colorChar);
                char textChar = TEXT_TO_DISPLAY.charAt(i);
                sb.append("" + color + textChar);
            }
            return sb.toString();
        }));
//        textBuilder.setString(new Constant<>(ChatColor.GREEN + "Sky" + ChatColor.RED + "tale"));
        textBuilder.setFontSize(new Constant<>(10.0d));
        textBuilder.setFontFileName("KGEverSinceNewYork.ttf");
    }
}
