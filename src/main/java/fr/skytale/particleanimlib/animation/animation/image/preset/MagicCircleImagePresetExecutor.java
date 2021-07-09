package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

public class MagicCircleImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public MagicCircleImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder) {

        imageBuilder.setImageFileName("magic_circle_ext.png");
        imageBuilder.setDirectorVectors(new Vector(0.2, 0, 0), new Vector(0, 0, 0.2));
        imageBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(-Math.PI / 100));
        imageBuilder.setTicksDuration(400);
        imageBuilder.setShowFrequency(new Constant<>(3));

        ImageBuilder midImageBuilder = new ImageBuilder();
        midImageBuilder.setPosition(imageBuilder.getPosition());
        midImageBuilder.setJavaPlugin(imageBuilder.getJavaPlugin());

        midImageBuilder.setImageFileName("magic_circle_mid.png");
        midImageBuilder.setDirectorVectors(new Vector(0.2, 0, 0), new Vector(0, 0, 0.2));
        midImageBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 40));
        midImageBuilder.setTicksDuration(400);
        midImageBuilder.setShowFrequency(new Constant<>(3));

        ImageBuilder inImageBuilder = new ImageBuilder();
        inImageBuilder.setPosition(imageBuilder.getPosition());
        inImageBuilder.setJavaPlugin(imageBuilder.getJavaPlugin());

        inImageBuilder.setImageFileName("magic_circle_in.png");
        inImageBuilder.setDirectorVectors(new Vector(0.2, 0, 0), new Vector(0, 0, 0.2));
        inImageBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(-Math.PI / 23));
        inImageBuilder.setTicksDuration(400);
        inImageBuilder.setShowFrequency(new Constant<>(3));

        Image extImage = imageBuilder.getAnimation();
        Image midImage = midImageBuilder.getAnimation();
        Image inImage = inImageBuilder.getAnimation();

        imageBuilder.setTicksDuration(1);
        imageBuilder.setShowFrequency(1);
        imageBuilder.setCallback(result -> {
            extImage.show();
            midImage.show();
            inImage.show();
        });
    }
}
