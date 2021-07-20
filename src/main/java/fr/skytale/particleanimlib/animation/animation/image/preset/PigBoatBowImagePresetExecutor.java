package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PigBoatBowImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public PigBoatBowImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder, JavaPlugin plugin) {

        if (imageBuilder.getPosition() == null || imageBuilder.getJavaPlugin() == null) {
            throw new IllegalArgumentException("This particular preset requires to define the Position and the JavaPlugin before calling builder.applyPreset(AnimationPreset) method.");
        }
        Location originLocation = imageBuilder.getPosition().getType() == APosition.Type.ENTITY ? imageBuilder.getPosition().getMovingEntity().getLocation().add(imageBuilder.getPosition().getRelativeLocation().getCurrentValue(0)) : imageBuilder.getPosition().getLocation().getCurrentValue(0);
        imageBuilder.setImageFileName("bow.png");
        imageBuilder.setDirectorVectors(new Vector(-0.2, 0, 0), new Vector(0, 0.2, 0));
        int showPeriod = 2;
        imageBuilder.setShowPeriod(showPeriod);
        imageBuilder.setPosition(APosition.fromLocation(originLocation));
        double stepAngle = Math.PI / 200;
        int acceleration_tick_duration = 129;
        double maxRotation = stepAngle * acceleration_tick_duration / showPeriod;
        int still_duration = 40;
        imageBuilder.setTicksDuration(acceleration_tick_duration * 2 + still_duration);

        imageBuilder.setRotation(new Vector(0, 1, 0), new CallbackWithPreviousValueVariable<>(maxRotation, (iterationCount, previousValue) -> {
            //0->60   (60) : rotation deceleration
            if (iterationCount <= acceleration_tick_duration) return previousValue - stepAngle;
                //60->100 (40) : still
            else if (iterationCount <= (acceleration_tick_duration + still_duration)) {
                return 0.0;
            }
            //100->160 (60) : rotation acceleration
            else return previousValue + stepAngle;
        }));
        Image imageBow = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("boat.png");
        imageBuilder.setCallback(result -> imageBow.show());
        Image imageBoat = imageBuilder.getAnimation();


        imageBuilder.setImageFileName("pig.png");
        imageBuilder.setDirectorVectors(new Vector(0, 0, -0.2), new Vector(0, 0.2, 0));
        imageBuilder.setPosition(APosition.fromLocation(new CallbackWithPreviousValueVariable<>(originLocation, (iterationCount, previousValue) -> {
            if (iterationCount == 2) {
                return previousValue.add(new Vector(0.5, 0, 0));
            } else if (iterationCount == 4) {
                return previousValue.add(new Vector(-0.5, 0, 0));
            } else {
                return previousValue;
            }
        })));
        imageBuilder.setTicksDuration(acceleration_tick_duration + still_duration);
        imageBuilder.setRotation(new Vector(0, 1, 0), new CallbackWithPreviousValueVariable<>(0.0, (iterationCount, previousValue) -> {
            //0->40   (40) : still
            if (iterationCount <= still_duration) return 0.0;
                //41->100 (60) : rotation acceleration
            else return previousValue + stepAngle;
        }));
        imageBuilder.setCallback(result -> imageBoat.show());
    }
}
