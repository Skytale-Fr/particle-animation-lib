package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationCircleRotating3PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationCircleRotating3PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 1, 0));
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRadius(1);
        circleBuilder.setTicksDuration(20);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setRotation(new Vector(0, 0, 1), Math.PI / 25);
        Circle circle = circleBuilder.getAnimation();
        circleBuilder.setNbPoints(8, true);
        circleBuilder.setRotation(new Vector(0, 0, 1), Math.PI / 25);
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(400);
        circleBuilder.setShowPeriod(new Constant<>(20));
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(circle, new SubAnimOrientationConfig(SubAnimOrientationModifier.NO_ADDITIONAL_ORIENTATION)));
    }
}