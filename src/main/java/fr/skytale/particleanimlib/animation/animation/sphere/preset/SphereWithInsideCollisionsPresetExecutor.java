package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.action.EntityCollisionActionCallbackPresets;
import fr.skytale.particleanimlib.animation.collision.processor.SimpleCollisionProcessor;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPresets;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SphereWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereWithInsideCollisionsPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbPoints(64);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(5);
        sphereBuilder.addCollisionHandler(createCollisionBuilder(sphereBuilder).build());
    }

    private CollisionBuilder<Entity, SphereTask> createCollisionBuilder(SphereBuilder sphereBuilder) {
        CollisionBuilder<Entity, SphereTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return Objects.requireNonNull(currentIterationBaseLocation.getWorld()).getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(
                sphereBuilder,
                EntityCollisionCheckPresets.TARGET_CENTER_INSIDE_SPHERE,
                EntityCollisionActionCallbackPresets.displayParticle(
                        new ParticleTemplate(Particle.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));

        return collisionBuilder;
    }

}
