package fr.skytale.particleanimlib.animation.animation.text.preset;

import fr.skytale.particleanimlib.animation.animation.text.TextBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ContentEvolvingTextKGEverSinceNewYorkPresetExecutor extends AAnimationPresetExecutor<TextBuilder> {

    public ContentEvolvingTextKGEverSinceNewYorkPresetExecutor() {
        super(TextBuilder.class);
    }

    @Override
    protected void apply(TextBuilder textBuilder, JavaPlugin plugin) {
        if (textBuilder.getPosition() == null || textBuilder.getJavaPlugin() == null) {
            throw new IllegalArgumentException("This particular preset requires to define the Position and the JavaPlugin before calling builder.applyPreset(AnimationPreset) method.");
        }

        Location originLocation = textBuilder.getOriginLocation();

        textBuilder.setPosition(new LocatedAnimationPosition(new CallbackWithPreviousValueVariable<>(originLocation, (iterationCount, previousValue) -> {
            if (iterationCount > 70 && iterationCount % 10 >= 2) {
                double scale = 3.0d;
                return originLocation.clone().add(new Vector(Math.random(), Math.random(), Math.random()).multiply(scale));
            } else {
                return previousValue;
            }
        })));

        textBuilder.setDirectorVectors(new Vector(0, 0, 1), new Vector(0, 1, 0));
        textBuilder.setTicksDuration(150);
        textBuilder.setShowPeriod(new Constant<>(1));
        textBuilder.setString(new CallbackVariable<>((iterationCount) -> {
            if (iterationCount < 70) {
                int letterCount = (int) Math.floor(iterationCount / 10.0d);
                return "Skytale".substring(0, letterCount);
            } else {
                if (iterationCount % 10 < 2) {
                    return "Skytale";
                } else return "";
            }
        }));
        textBuilder.setFontSize(new Constant<>(10.0d));
        textBuilder.setFontFileName("KGEverSinceNewYork.ttf");
    }

}
