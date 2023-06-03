package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CountdownImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public CountdownImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder, JavaPlugin plugin) {

        if (imageBuilder.getPosition() == null || imageBuilder.getJavaPlugin() == null) {
            throw new IllegalArgumentException("This particular preset requires to define the Position and the JavaPlugin before calling builder.applyPreset(AnimationPreset) method.");
        }

        Location originLocation = imageBuilder.getOriginLocation();

        imageBuilder.setRotation(new Vector(0, 0, 1), new Vector(0, -1, 0));
        imageBuilder.setImageFileName("race_countdown_go.png");
        imageBuilder.setTicksDuration(20);
        imageBuilder.setShowPeriod(new Constant<>(2));
        imageBuilder.setPosition(new LocatedAnimationPosition(new CallbackWithPreviousValueVariable<>(
                originLocation,
                (iterationCount, previousValue) -> {
                    if (iterationCount == 2 || iterationCount == 4) {
                        return previousValue.add(new Vector(-0.5, 0, 0));
                    } else if (iterationCount > 4) {
                        return previousValue.add(new Vector(-0.2, 0, 0));
                    } else {
                        return previousValue;
                    }
                })
        ));
        imageBuilder.setScale(new CallbackVariable<>(iterationCount -> 0.2f + ((float)Math.exp(iterationCount/4f)/400f)));
        Image imageGo = imageBuilder.getAnimation();


        imageBuilder.setPosition(new LocatedAnimationPosition(new CallbackWithPreviousValueVariable<>(
                originLocation,
                (iterationCount, previousValue) -> {
                    if (iterationCount == 2 || iterationCount == 4) {
                        return previousValue.add(new Vector(-0.5, 0, 0));
                    } else if (iterationCount == 6 || iterationCount == 8) {
                        return previousValue.add(new Vector(0.5, 0, 0));
                    } else {
                        return previousValue;
                    }
                })
        ));
        imageBuilder.setScale(new Constant<>(0.2f));
        imageBuilder.setImageFileName("race_countdown_1.png");
        imageBuilder.setAnimationEndedCallback(result -> imageGo.show());
        Image image1 = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("race_countdown_2.png");
        imageBuilder.setAnimationEndedCallback(result -> image1.show());
        Image image2 = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("race_countdown_3.png");
        imageBuilder.setAnimationEndedCallback(result -> image2.show());
        Image image3 = imageBuilder.getAnimation();


        imageBuilder.setImageFileName("race_countdown_4.png");
        imageBuilder.setAnimationEndedCallback(result -> image3.show());
        Image image4 = imageBuilder.getAnimation();


        imageBuilder.setImageFileName("race_countdown_5.png");
        imageBuilder.setAnimationEndedCallback(result -> image4.show());
    }
}
