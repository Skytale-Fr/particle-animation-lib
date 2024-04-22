package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
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
import org.bukkit.util.Vector;

import java.util.Objects;

public class CuboidWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidWithInsideCollisionsPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder, JavaPlugin plugin) {
        cuboidBuilder.setFromLocationToFirstCorner(new Vector(-4, -4, -4));
        cuboidBuilder.setFromLocationToSecondCorner(new Vector(4, 4, 4));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setTicksDuration(100);
        cuboidBuilder.setShowPeriod(new Constant<>(3));
        cuboidBuilder.addCollisionHandler(createCollisionBuilder(cuboidBuilder).build());
    }

    private CollisionBuilder<Entity, CuboidTask> createCollisionBuilder(CuboidBuilder cuboidBuilder) {
        CollisionBuilder<Entity, CuboidTask> collisionBuilder = new CollisionBuilder<>();
        collisionBuilder.setJavaPlugin(cuboidBuilder.getJavaPlugin());
        collisionBuilder.setPotentialCollidingTargetsCollector(lineTask -> {
            Location currentIterationBaseLocation = lineTask.getCurrentIterationBaseLocation();
            return Objects.requireNonNull(currentIterationBaseLocation.getWorld()).getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(
                cuboidBuilder,
                EntityCollisionCheckPresets.EXACT_BOUNDING_BOX_INSIDE_CUBOID,
                EntityCollisionActionCallbackPresets.displayParticle(
                        new ParticleTemplate(Particle.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));

        return collisionBuilder;
    }

}
