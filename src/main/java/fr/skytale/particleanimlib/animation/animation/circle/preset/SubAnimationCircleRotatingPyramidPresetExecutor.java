package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SubAnimationCircleRotatingPyramidPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SubAnimationCircleRotatingPyramidPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        PyramidBuilder pyramidBuilder = new PyramidBuilder();
        pyramidBuilder.setPosition(circleBuilder.getPosition());
        pyramidBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        pyramidBuilder.applyPreset(AnimationPreset.PYRAMID, plugin);
        pyramidBuilder.setFromCenterToApex(new Vector(0, 0, 4));
        pyramidBuilder.setTicksDuration(2);
        pyramidBuilder.setShowFrequency(1);

        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 1, 0));
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setRadius(8);
        circleBuilder.setTicksDuration(600);
        circleBuilder.setShowFrequency(new Constant<>(2));
        circleBuilder.setRotation(new Constant<>(new Vector(1, 0, 0)), new DoublePeriodicallyEvolvingVariable(Math.PI / 500, Math.PI / 200, 3));
        circleBuilder.setPointDefinition(PointDefinition.fromSubAnim(pyramidBuilder.getAnimation()));
    }
}
