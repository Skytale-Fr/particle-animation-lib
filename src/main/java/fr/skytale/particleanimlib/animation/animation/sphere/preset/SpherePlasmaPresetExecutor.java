package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.CallbackPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class SpherePlasmaPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SpherePlasmaPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setPosition(sphereBuilder.getPosition());
        lineBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        lineBuilder.setTicksDuration(60);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));
        lineBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.fromRGB(255, 170, 0)));

        sphereBuilder.setRadius(1);
        sphereBuilder.setNbPoints(32);
        sphereBuilder.setPointDefinition(new CallbackPointDefinition(
                (pointLocation, animation, task, fromAnimCenterToPoint, fromPreviousToCurrentAnimBaseLocation) -> {
                    lineBuilder.setPosition(new DirectedLocationAnimationPosition(
                            pointLocation,
                            fromAnimCenterToPoint,
                            1.0));
                    lineBuilder.setPoint1OnPosition();
                    lineBuilder.setFromPositionToPoint2(new Constant<>(fromAnimCenterToPoint), new DoublePeriodicallyEvolvingVariable(1d, 0.1, 1));
                    lineBuilder.getAnimation().show().setParentTask(task);
                }
        ));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(10);
    }
}
