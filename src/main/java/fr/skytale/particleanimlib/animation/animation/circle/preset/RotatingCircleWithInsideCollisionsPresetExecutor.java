package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.circle.CircleTask;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.action.EntityCollisionActionCallbackPresets;
import fr.skytale.particleanimlib.animation.collision.precheck.PotentialEntityTargetFilters;
import fr.skytale.particleanimlib.animation.collision.processor.SimpleCollisionProcessor;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPresets;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RotatingCircleWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public RotatingCircleWithInsideCollisionsPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        AnimationPreset.CIRCLE_ROTATING.apply(circleBuilder, plugin);
        circleBuilder.setTicksDuration(400);
        circleBuilder.addCollisionHandler(createCollisionBuilder(circleBuilder).build());
    }

    private CollisionBuilder<Entity, CircleTask> createCollisionBuilder(CircleBuilder circleBuilder) {
        CollisionBuilder<Entity, CircleTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return Objects.requireNonNull(currentIterationBaseLocation.getWorld()).getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter(PotentialEntityTargetFilters.isType(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(
                SimpleCollisionProcessor.useDefault(
                        circleBuilder,
                        EntityCollisionCheckPresets.TARGET_CENTER_INSIDE_CIRCLE,
                        EntityCollisionActionCallbackPresets.displayParticle(
                                new ParticleTemplate(Particle.EXPLOSION_HUGE),
                                AViewers.fromNearbyPlayers(50),
                                1
                        )
                )
        );


        return collisionBuilder;
    }

}
