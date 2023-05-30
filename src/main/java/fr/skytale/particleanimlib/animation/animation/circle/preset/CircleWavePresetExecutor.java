package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.wave.Wave;
import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class CircleWavePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public CircleWavePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        final int halfCircleDuration = 30;
        circleBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 1, 0));
        circleBuilder.setNbPoints(10, true);
        circleBuilder.setRadius(new CallbackWithPreviousValueVariable<>(3.0, (iterationCount, previousValue) -> {
            if (iterationCount < halfCircleDuration)
                return previousValue - Math.sin(iterationCount) - (5 / halfCircleDuration);
            else
                return previousValue + Math.sin(halfCircleDuration - (iterationCount % halfCircleDuration)) +
                       (5 / halfCircleDuration);
        }));
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FIREWORKS_SPARK));
        circleBuilder.setTicksDuration(halfCircleDuration * 2);
        circleBuilder.setShowPeriod(2);
        Location animationLoc = circleBuilder.getOriginLocation();
        final Vector moveStep = new Vector(0, 0, 0.5);
        circleBuilder.setPosition(new LocatedAnimationPosition(new CallbackVariable<>(iterationCount -> {
            if (iterationCount < halfCircleDuration)
                return animationLoc.clone().add(moveStep.clone().multiply(iterationCount));
            else
                return animationLoc.clone().add(moveStep.clone().multiply(
                        halfCircleDuration - (iterationCount % halfCircleDuration)));
        })));

        WaveBuilder waveBuilder = new WaveBuilder();
        waveBuilder.setJavaPlugin(plugin);
        waveBuilder.setPosition(new LocatedAnimationPosition(animationLoc.clone()));
        waveBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FIREWORKS_SPARK));
        waveBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 1, 0));
        waveBuilder.setTicksDuration(40);
        waveBuilder.setShowPeriod(2);
        waveBuilder.setNbPoints(15, true);
        waveBuilder.setRadiusMax(20);
        waveBuilder.setRadiusStart(3);
        waveBuilder.setRadiusStep(0.2);
        waveBuilder.setPositiveHeight(false);
        Wave waveAnimation = waveBuilder.getAnimation();
        circleBuilder.addAnimationEndedCallback(result -> {
            waveAnimation.show();
        });
    }
}
