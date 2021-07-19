package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationParabolaRotatingPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationParabolaRotatingPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        ParabolaBuilder parabolaBuilder = new ParabolaBuilder();
        parabolaBuilder.applyPreset(AnimationPreset.PARABOLA, plugin);
        parabolaBuilder.setShowFrequency(2);
        parabolaBuilder.setTicksDuration(40);
        parabolaBuilder.setBulletLifetime(20);

        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(new Vector(0, 1, 0), new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 200, 3));
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowFrequency(20);
        circleBuilder.setPointDefinition(APointDefinition.fromSubAnim(parabolaBuilder.getAnimation(), 1.0, (v) -> v.multiply(-1).add(new Vector(0, 0.5, 0))));
    }
}
