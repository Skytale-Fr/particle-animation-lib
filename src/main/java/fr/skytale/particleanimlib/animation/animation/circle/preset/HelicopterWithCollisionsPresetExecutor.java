package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineTask;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.EntityCollisionPreset;
import fr.skytale.particleanimlib.animation.collision.ParticleCollisionProcessor;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class HelicopterWithCollisionsPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    private static final int PROPELLER_COUNT = 3;
    private static final Orientation ORIENTATION = Orientation.UP; // The orientation of the circle handling all lines

    public HelicopterWithCollisionsPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setPosition(circleBuilder.getPosition());
        lineBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());

        lineBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        lineBuilder.setTicksDuration(1);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));
        lineBuilder.addCollisionHandler(createCollisionBuilder(lineBuilder).build());

        circleBuilder.setDirectorVectorsFromOrientation(ORIENTATION, 1);
        circleBuilder.setNbPoints(PROPELLER_COUNT, true);
        circleBuilder.setRadius(0.01);
        circleBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 8);
        circleBuilder.setPointDefinition(APointDefinition.fromSubAnim(lineBuilder.getAnimation(), 10.0d));
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowPeriod(new Constant<>(3));
    }

    private CollisionBuilder<Entity, LineTask> createCollisionBuilder(LineBuilder lineBuilder) {
        CollisionBuilder<Entity, LineTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> !entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(ParticleCollisionProcessor.useDefault(lineBuilder, EntityCollisionPreset.EXACT_BOUNDING_BOX, (animationTask, target) -> {
            if(!(target instanceof LivingEntity)) return -1;
            ((LivingEntity) target).damage(1);
            return 40; // The entity can only take damages every 20 ticks.
        }));

        return collisionBuilder;
    }

}
