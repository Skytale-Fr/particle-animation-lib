package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.CallbackPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SpherePlasmaPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SpherePlasmaPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setPosition(sphereBuilder.getPosition());
        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(new Constant<>(new Vector(0, 0, 1)), new Constant<>(3d));
        lineBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        lineBuilder.setTicksDuration(1);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));

        sphereBuilder.setRadius(1);
        sphereBuilder.setNbPoints(32);
        //sphereBuilder.setMainParticle(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 170, 0))));
        sphereBuilder.setPointDefinition(new CallbackPointDefinition(
                (pointLocation, animation, task, fromAnimCenterToPoint, fromPreviousToCurrentAnimBaseLocation) -> {
                    lineBuilder.setPosition(new DirectedLocationAnimationPosition(
                            pointLocation,
                            fromAnimCenterToPoint,
                            1.0));
                    lineBuilder.setPoint1OnPosition();
                    lineBuilder.setFromPositionToPoint2(new Constant<>(fromAnimCenterToPoint), new Constant<>(10d));
                    lineBuilder.getAnimation().show().setParentTask(task);
                }
        ));
        sphereBuilder.setPointDefinition(new SubAnimPointDefinition(
                lineBuilder.getAnimation(),
                new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION, 10.0d)
        ));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(5);
    }
}
