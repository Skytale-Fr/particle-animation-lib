package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.line.Line;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ExplodingLinesPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    private static final int PROPELLER_COUNT = 3;
    private static final Orientation ORIENTATION = Orientation.UP; // The orientation of the circle handling all lines

    public ExplodingLinesPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        lineBuilder.setPosition(circleBuilder.getPosition());
        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(new Constant<>(new Vector(0,0,1)), new Constant<>(3d));
        lineBuilder.setTicksDuration(80);
        lineBuilder.setShowPeriod(new Constant<>(2));
        lineBuilder.setNbPoints(new Constant<>(5));
        Line line = lineBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setTicksDuration(20 * 10);
        circleBuilder.setShowPeriod(5);
        circleBuilder.setRadius(2);
        circleBuilder.setRotation(
                new CallbackWithPreviousValueVariable<>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(
                                Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
                ),
                Math.PI / 10
        );
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(line, SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION,0.2));
    }
}
