package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.parabola.Parabola;
import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationParabolaRotating2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationParabolaRotating2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        ParabolaBuilder parabolaBuilder = new ParabolaBuilder();
        parabolaBuilder.applyPreset(AnimationPreset.PARABOLA, plugin);
        parabolaBuilder.setShowPeriod(2);
        parabolaBuilder.setTicksDuration(70);
        parabolaBuilder.setBulletLifetime(50);
        parabolaBuilder.setRotation(new PARotation(AAnimationTask.W, AAnimationTask.W.clone().multiply(-1)));
        parabolaBuilder.setPosition(circleBuilder.getPosition());
        final Parabola parabola = parabolaBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
                new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 100, 1)
        );
        circleBuilder.setRadius(2);
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowPeriod(40);
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(parabola, new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION, 0.2)));
    }
}
