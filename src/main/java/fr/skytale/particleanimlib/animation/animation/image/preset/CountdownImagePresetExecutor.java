package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
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

        Location originLocation = imageBuilder.getPosition().getType() == APosition.Type.ENTITY ? imageBuilder.getPosition().getMovingEntity().getLocation().add(imageBuilder.getPosition().getRelativeLocation().getCurrentValue(0)) : imageBuilder.getPosition().getLocation().getCurrentValue(0);

        imageBuilder.setDirectorVectors(new Vector(0, 0, -0.2), new Vector(0, 0.2, 0));
        imageBuilder.setImageFileName("race_countdown_go.png");
        imageBuilder.setTicksDuration(20);
        imageBuilder.setShowPeriod(new Constant<>(2));
        imageBuilder.setPosition(APosition.fromLocation(new CallbackWithPreviousValueVariable<>(originLocation, (iterationCount, previousValue) -> {
            if (iterationCount == 2) {
                return previousValue.add(new Vector(0.5, 0, 0));
            } else if (iterationCount == 4) {
                return previousValue.add(new Vector(-0.5, 0, 0));
            } else {
                return previousValue;
            }
        })));
        Image imageGo = imageBuilder.getAnimation();


        imageBuilder.setPosition(APosition.fromLocation(originLocation));
        imageBuilder.setImageFileName("race_countdown_1.png");
        imageBuilder.addAnimationEndedCallback(result -> imageGo.show());
        Image image1 = imageBuilder.getAnimation();


        imageBuilder.setPosition(APosition.fromLocation(originLocation));
        imageBuilder.setImageFileName("race_countdown_2.png");
        imageBuilder.addAnimationEndedCallback(result -> image1.show());
        Image image2 = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("race_countdown_3.png");
        imageBuilder.addAnimationEndedCallback(result -> image2.show());
        Image image3 = imageBuilder.getAnimation();


        imageBuilder.setImageFileName("race_countdown_4.png");
        imageBuilder.addAnimationEndedCallback(result -> image3.show());
        Image image4 = imageBuilder.getAnimation();


        imageBuilder.setImageFileName("race_countdown_5.png");
        imageBuilder.addAnimationEndedCallback(result -> image4.show());
    }
}
