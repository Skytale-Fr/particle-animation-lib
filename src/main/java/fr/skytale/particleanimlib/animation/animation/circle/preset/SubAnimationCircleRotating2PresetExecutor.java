package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SubAnimationCircleRotating2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationCircleRotating2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(10, true);
        circleBuilder.setRadius(2);
        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(40);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 10));
        Circle circle = circleBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 25));
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(400);
        circleBuilder.setShowPeriod(new Constant<>(40));
        circleBuilder.setPointDefinition(APointDefinition.fromSubAnim(circle));
    }
}
