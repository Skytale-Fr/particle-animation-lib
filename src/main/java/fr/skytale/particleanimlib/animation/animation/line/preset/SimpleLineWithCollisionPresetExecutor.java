package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.action.EntityCollisionActionCallbackPresets;
import fr.skytale.particleanimlib.animation.collision.handler.CollisionHandler;
import fr.skytale.particleanimlib.animation.collision.processor.ParticleCollisionProcessor;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPresets;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Objects;

public class SimpleLineWithCollisionPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public SimpleLineWithCollisionPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {
        // Fetch the correct direction to set
        // from the current type of the position
        // (if the particle animation was set on a block
        // or is linked to an entity).
        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(new Constant<>(new Vector(1, 0, 0)), new Constant<>(10d));
        lineBuilder.setPointDefinition(new ParticleTemplate(Particle.CRIT));
        lineBuilder.setNbPoints(20);
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));

        CollisionBuilder<Entity, LineTask> collisionBuilder = createCollisionBuilder(lineBuilder);
        CollisionHandler<Entity, LineTask> collisionHandler = collisionBuilder.build();
        lineBuilder.addCollisionHandler(collisionHandler);
    }

    private CollisionBuilder<Entity, LineTask> createCollisionBuilder(LineBuilder lineBuilder) {
        CollisionBuilder<Entity, LineTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return Objects.requireNonNull(currentIterationBaseLocation.getWorld()).getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(ParticleCollisionProcessor.useDefault(
                lineBuilder,
                EntityCollisionCheckPresets.EXACT_BOUNDING_BOX,
                EntityCollisionActionCallbackPresets.displayParticle(
                        new ParticleTemplate(Particle.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));

        return collisionBuilder;
    }

}
