package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
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

public class CuboidRotatingResizingWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidRotatingResizingWithInsideCollisionsPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder, JavaPlugin plugin) {
        cuboidBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.toRadians(0), Math.toRadians(1), 0));
        cuboidBuilder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-3, -3, -3), new Vector(0.05, 0.1, 0.05), 10));
        cuboidBuilder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(3, 3, 3), new Vector(-0.05, -0.1, -0.05), 10));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowPeriod(new Constant<>(1));
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
                        new ParticleTemplate(ParticleEffect.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));

        return collisionBuilder;
    }
}
