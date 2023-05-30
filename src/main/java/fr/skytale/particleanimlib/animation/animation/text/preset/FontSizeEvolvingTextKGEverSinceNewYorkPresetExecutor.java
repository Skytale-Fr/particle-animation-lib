package fr.skytale.particleanimlib.animation.animation.text.preset;

import fr.skytale.particleanimlib.animation.animation.text.TextBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class FontSizeEvolvingTextKGEverSinceNewYorkPresetExecutor extends AAnimationPresetExecutor<TextBuilder> {

    public FontSizeEvolvingTextKGEverSinceNewYorkPresetExecutor() {
        super(TextBuilder.class);
    }

    @Override
    protected void apply(TextBuilder textBuilder, JavaPlugin plugin) {
        textBuilder.setRotation(new Vector(0, 0, 1), new Vector(0, 1, 0));
        textBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WATER_BUBBLE, new Color(255, 170, 0)));
        textBuilder.setTicksDuration(100);
        textBuilder.setShowPeriod(new Constant<>(1));
        textBuilder.setString(new Constant<>("Coucou"));
        textBuilder.setFontSize(new CallbackVariable<>(iterationCount -> 10.0d +
                                                                         5.0d * Math.cos(iterationCount * 0.1d)));
        textBuilder.setFontFileName("KGEverSinceNewYork.ttf");
    }
}
