package fr.skytale.particleanimlib.animation.animation.image.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.image.Image;
import fr.skytale.particleanimlib.animation.animation.image.ImageBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.function.Function;

public class PA205MarqueMagiquePresetExecutor extends AAnimationPresetExecutor<ImageBuilder> {

    public PA205MarqueMagiquePresetExecutor() {
        super(ImageBuilder.class);
    }

    @Override
    protected void apply(ImageBuilder imageBuilder, JavaPlugin plugin) {
        if (imageBuilder.getPosition() == null || imageBuilder.getJavaPlugin() == null) {
            throw new IllegalArgumentException("This particular preset requires to define the Position and the JavaPlugin before calling builder.applyPreset(AnimationPreset) method.");
        }
        ;
        int durationTicks = 120;
        int halfDurationTicks = durationTicks / 2;
        final Function<Integer, Double> angleCurve = x -> Math.pow((x/5d), 3)/550;

        //Magic particles
        CircleBuilder magicBuilder = new CircleBuilder();
        magicBuilder.setJavaPlugin(plugin);
        magicBuilder.setPosition(imageBuilder.getPosition());
        magicBuilder.setRadius(0.1);
        magicBuilder.setNbPoints(1);
        magicBuilder.setPointDefinition(new ParticleTemplate(Particle.SPELL_WITCH, 1, 0.1f, new Vector(1,1,1)));
        magicBuilder.setTicksDuration(durationTicks*2);

        //Image
        imageBuilder.setShowPeriod(1);

        //Image sulfur
        imageBuilder.setImageFileName("sulfur_25_25.png");
        imageBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, -1, 0),
                new Constant<>(new Vector(0, 1, 0)),
                new CallbackVariable<>(
                        iterationCount -> angleCurve.apply(iterationCount - halfDurationTicks)
                ));
        imageBuilder.setTicksDuration(halfDurationTicks);
        Image imageSulfur = imageBuilder.getAnimation();

        //Image mercury
        imageBuilder.setImageFileName("mercury_27_27.png");
        imageBuilder.setTicksDuration(durationTicks);
        imageBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, -1, 0),
                new Constant<>(new Vector(0, 1, 0)),
                new CallbackVariable<>(
                        iterationCount -> angleCurve.apply(iterationCount - halfDurationTicks)
                ));
        imageBuilder.setAnimationEndedCallback(result -> imageSulfur.show());
        Image imageMercury = imageBuilder.getAnimation();

        //Image salt
        imageBuilder.setImageFileName("salt_15_15.png");
        imageBuilder.setTicksDuration(halfDurationTicks);
        imageBuilder.setRotation(
                new Vector(0, 0, 1),
                new Vector(0, -1, 0),
                new Vector(0, 1, 0),
                new CallbackVariable<>(
                        angleCurve::apply
                ));
        imageBuilder.setAnimationEndedCallback(result -> imageMercury.show());
        Image imageSalt = imageBuilder.getAnimation();

        //Launching chained images + magic particles
        imageBuilder.setTicksDuration(1);
        imageBuilder.setAnimationEndedCallback(animationEnding -> {
            magicBuilder.getAnimation().show();
            imageSalt.show();
        });
    }
}
