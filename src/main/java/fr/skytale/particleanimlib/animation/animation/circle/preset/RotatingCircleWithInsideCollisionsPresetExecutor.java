package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.EntityCollisionPreset;
import fr.skytale.particleanimlib.animation.collision.SimpleCollisionProcessor;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class RotatingCircleWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public RotatingCircleWithInsideCollisionsPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        AnimationPreset.CIRCLE.apply(circleBuilder, plugin);
        circleBuilder.setTicksDuration(400);
        circleBuilder.setRotation(new Vector(1, 0, 0), Math.PI / 40d);
        circleBuilder.addCollisionHandler(createCollisionBuilder(circleBuilder).build());
    }

    private CollisionBuilder<Entity, CircleTask> createCollisionBuilder(CircleBuilder circleBuilder) {
        CollisionBuilder<Entity, CircleTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> !entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(circleBuilder, EntityCollisionPreset.TARGET_CENTER_INSIDE_CIRCLE, (animationTask, target) -> {
            if(!(target instanceof LivingEntity)) return -1;
            ((LivingEntity) target).damage(1);
            return 20; // The entity can only take damages every 20 ticks.
        }));
        

        return collisionBuilder;
    }

}
