package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PigBoatBowImagePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public PigBoatBowImagePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder, JavaPlugin plugin) {

        if (imageBuilder.getPosition() == null || imageBuilder.getJavaPlugin() == null) {
            throw new IllegalArgumentException("This particular preset requires to define the Position and the JavaPlugin before calling builder.applyPreset(AnimationPreset) method.");
        }
        imageBuilder.setImageFileName("bow.png");
        imageBuilder.setShowPeriod(1);
        int durationTicks = 120;
        int halfDurationTicks = durationTicks / 2;

        final Function<Integer, Double> angleCurve = x -> Math.pow((x/5d), 3)/550;

        imageBuilder.setDirectorVectorsAndRotation(
                new Vector(1, 0, 0),
                new Vector(0, -1, 0),
                new Constant<>(new Vector(0, 1, 0)),
                new CallbackVariable<>(
                        iterationCount -> angleCurve.apply(iterationCount - halfDurationTicks)
                ));
        imageBuilder.setTicksDuration(halfDurationTicks);
        Image imageBow = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("boat.png");
        imageBuilder.setTicksDuration(durationTicks);
        imageBuilder.setDirectorVectorsAndRotation(
                new Vector(1, 0, 0),
                new Vector(0, -1, 0),
                new Constant<>(new Vector(0, 1, 0)),
                new CallbackVariable<>(
                        iterationCount -> angleCurve.apply(iterationCount - halfDurationTicks)
                ));
        imageBuilder.setAnimationEndedCallback(result -> imageBow.show());
        Image imageBoat = imageBuilder.getAnimation();

        imageBuilder.setImageFileName("pig.png");
        imageBuilder.setDirectorVectors(new Vector(0, 0, -0.2), new Vector(0, 0.2, 0));
        imageBuilder.setTicksDuration(halfDurationTicks);
        imageBuilder.setDirectorVectorsAndRotation(
                new Vector(0, 0, 1),
                new Vector(0, -1, 0),
                new Vector(0, 1, 0),
                new CallbackVariable<>(
                        angleCurve::apply
                ));
        imageBuilder.setTicksDuration(halfDurationTicks);
        imageBuilder.setAnimationEndedCallback(result -> imageBoat.show());
    }
}
