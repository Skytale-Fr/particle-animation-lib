package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SubAnimationCircleRotatingPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationCircleRotatingPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder) {
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(10, true);
        circleBuilder.setRadius(2);
        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowFrequency(new Constant<>(2));
        Circle circle = circleBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 200, 3));
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowFrequency(new Constant<>(2));
        circleBuilder.setPointDefinition(PointDefinition.fromSubAnim(circle));
    }
}
