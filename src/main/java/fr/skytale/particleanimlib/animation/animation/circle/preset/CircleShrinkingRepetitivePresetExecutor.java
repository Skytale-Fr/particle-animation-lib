package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CircleShrinkingRepetitivePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public CircleShrinkingRepetitivePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        //Original
        double startRadius = 20d;
        double endRadius = 4d;
        int tickDuration = 20 * 5;

        circleBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(0, 0, 1),
                new Vector(1, 0, 0),
                Math.PI / 48);

        circleBuilder.setNbPoints((int) (startRadius / 4), true);

        double radiusChangeValue = (endRadius - startRadius) / tickDuration;
        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(startRadius, radiusChangeValue));

        ParticleTemplate particleTemplate = new ParticleTemplate(Particle.END_ROD);
        particleTemplate.extra(0.05f);

        circleBuilder.setPointDefinition(particleTemplate);
        circleBuilder.setTicksDuration(tickDuration);
        Circle originalAnimation = circleBuilder.getAnimation();

        //
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(20 * 2);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, 1, 0.8f, new Vector(0, 0, 0)));

        //3rd repetition
        circleBuilder.setAnimationEndedCallback(task -> originalAnimation.show());
        Circle circle_3 = circleBuilder.getAnimation();

        //2nd repetition
        circleBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_3.show();
        });
        Circle circle_2 = circleBuilder.getAnimation();

        //1rst repetition
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_2.show();
        });
    }
}
