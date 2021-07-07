package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SkytaleImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public SkytaleImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder) {
        imageBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 20));
        imageBuilder.setDirectorVectors(new Vector(0, 0, 0.2), new Vector(0, 0.2, 0));
        imageBuilder.setImageFileName("skytale.png");

        imageBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        imageBuilder.setTicksDuration(400);
        imageBuilder.setShowFrequency(new Constant<>(2));
    }
}
