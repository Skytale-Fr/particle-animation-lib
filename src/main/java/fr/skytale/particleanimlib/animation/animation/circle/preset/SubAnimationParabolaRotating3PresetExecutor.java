package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.parabola.Parabola;
import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationParabolaRotating3PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationParabolaRotating3PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        ParabolaBuilder parabolaBuilder = new ParabolaBuilder();
        parabolaBuilder.applyPreset(AnimationPreset.PARABOLA, plugin);
        parabolaBuilder.setShowPeriod(2);
        parabolaBuilder.setTicksDuration(100);
        parabolaBuilder.setBulletLifetime(100);
        parabolaBuilder.setBulletShootPeriod(100);
        parabolaBuilder.setDirection(new Vector(-0.5, 2, 0));
        parabolaBuilder.setPosition(circleBuilder.getPosition());
        Parabola parabola = parabolaBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setDirectorVectorsAndRotation(
                new Vector(1, 0, 0),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
                new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 300, 1)
        );
        circleBuilder.setRadius(3);
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowPeriod(15);
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(
                parabola,
                new SubAnimOrientationConfig(
                        SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION,
                        0d)
        ));
    }
}
