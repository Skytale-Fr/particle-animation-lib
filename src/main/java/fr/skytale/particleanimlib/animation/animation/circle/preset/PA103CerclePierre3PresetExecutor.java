package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.DustColorTransitionData;

import java.awt.*;

public class PA103CerclePierre3PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA103CerclePierre3PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        double radius = 6d;
        circleBuilder.setTicksDuration(Integer.MAX_VALUE);

        //Inner circle
        circleBuilder.setRadius(radius / 2);
        circleBuilder.setNbPoints((int) radius, true);
        circleBuilder.setPointDefinition(
                new ParticleTemplate(
                        ParticleEffect.DUST_COLOR_TRANSITION,
                        1,
                        1f,
                        new Vector(radius / 4, 0.2, radius / 4),
                        new DustColorTransitionData(Color.MAGENTA, Color.WHITE, 1f)
                )
        );
        Circle innerCircle = circleBuilder.getAnimation();

        //Outer circle
        circleBuilder.setRadius(radius);
        circleBuilder.setNbPoints(80, true);
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.DRAGON_BREATH, 0.005f));
        circleBuilder.setRotation(
                new Vector(0, 1, 0),
                Math.PI / 100
        );
        AAnimationPosition position = (AAnimationPosition) circleBuilder.getPosition();
        circleBuilder.setPosition(
                new LocatedRelativeAnimationPosition(
                        position.toIVariableLocation(),
                        new CallbackVariable<>(iterationCount -> new Vector(0, Math.sin(iterationCount / 2d) + 1, 0))
                )
        );
        circleBuilder.setShowPeriod(2);
        Circle outerCircle = circleBuilder.getAnimation();

        //Combinating
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            outerCircle.setStopCondition(circleBuilder.getAnimation().getStopCondition());
            outerCircle.show();

            innerCircle.setStopCondition(circleBuilder.getAnimation().getStopCondition());
            innerCircle.show();
        });
    }
}
