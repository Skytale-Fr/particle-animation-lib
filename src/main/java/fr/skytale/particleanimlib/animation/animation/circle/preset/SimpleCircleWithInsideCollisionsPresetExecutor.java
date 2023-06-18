package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.action.EntityCollisionActionCallbackPresets;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPreset;
import fr.skytale.particleanimlib.animation.collision.processor.SimpleCollisionProcessor;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPresets;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Objects;

public class SimpleCircleWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public SimpleCircleWithInsideCollisionsPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(20, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowPeriod(new Constant<>(1));
        circleBuilder.addCollisionHandler(createCollisionBuilder(circleBuilder).build());
    }

    private CollisionBuilder<Entity, CircleTask> createCollisionBuilder(CircleBuilder circleBuilder) {
        CollisionBuilder<Entity, CircleTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return Objects.requireNonNull(currentIterationBaseLocation.getWorld()).getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(
                circleBuilder,
                EntityCollisionCheckPresets.TARGET_CENTER_INSIDE_CIRCLE,
                EntityCollisionActionCallbackPresets.displayParticle(
                        new ParticleTemplate(ParticleEffect.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));


        return collisionBuilder;
    }

}
