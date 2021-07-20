package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
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
        parabolaBuilder.setPosition(circleBuilder.getPosition());

        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRotation(new Vector(0, 1, 0), new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 100, 1));
        circleBuilder.setRadius(1.5);
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowPeriod(40);
        circleBuilder.setPointDefinition(APointDefinition.fromSubAnim(parabolaBuilder.getAnimation(), 1.0, (v) -> v.add(new Vector(0, 2, 0))));
    }
}
