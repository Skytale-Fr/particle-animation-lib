package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationCircleRotating2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationCircleRotating2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setRadius(2);
        circleBuilder.setNbPoints(20);
        circleBuilder.setShowPeriod(2);
        circleBuilder.setTicksDuration(40);
        Circle circle = circleBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setTicksDuration(20 * 20);
        circleBuilder.setShowPeriod(10);
        circleBuilder.setRotation(
                new CallbackWithPreviousValueVariable<>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(
                                Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
                ),
                Math.PI / 10
        );
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(circle, new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION, 0.5)));



        /*circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(10, true);
        circleBuilder.setRadius(2);
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 170, 0))));
        circleBuilder.setTicksDuration(40);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 10));
        Circle circle = circleBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new Constant<>(Math.PI / 25));
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(400);
        circleBuilder.setShowPeriod(new Constant<>(40));
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(circle));*/
    }
}
