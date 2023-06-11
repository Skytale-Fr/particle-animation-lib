package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.CallbackPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class ExplodingLinesInvertedPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    private static final int PROPELLER_COUNT = 3;
    private static final Orientation ORIENTATION = Orientation.UP; // The orientation of the circle handling all lines

    public ExplodingLinesInvertedPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        lineBuilder.setTicksDuration(40);
        lineBuilder.setShowPeriod(new Constant<>(2));
        lineBuilder.setNbPoints(new Constant<>(5));
        lineBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.SPELL_WITCH));

        circleBuilder.setPointDefinition(new CallbackPointDefinition(
                (pointLocation, animation, task, fromAnimCenterToPoint, fromPreviousToCurrentAnimBaseLocation) -> {
                    lineBuilder.setPosition(new DirectedLocationAnimationPosition(
                            pointLocation,
                            fromAnimCenterToPoint.clone().multiply(-1),
                            1.0));
                    lineBuilder.setPoint1OnPosition();
                    lineBuilder.setFromPositionToPoint2(new Constant<>(fromAnimCenterToPoint), new DoublePeriodicallyEvolvingVariable(1d, 0.1, 1));
                    lineBuilder.getAnimation().show().setParentTask(task);
                }
        ));
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setTicksDuration(20 * 10);
        circleBuilder.setShowPeriod(5);
        circleBuilder.setRadius(40);
        circleBuilder.setRotation(
                new CallbackWithPreviousValueVariable<>(
                        new Vector(0, 1, 0),
                        (iterationCount, previousValue) -> previousValue.add(new Vector(
                                Math.random() / 4, Math.random() / 4, Math.random() / 4)).normalize()
                ),
                Math.PI / 10
        );
    }
}
