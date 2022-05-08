package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidTask;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereTask;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.EntityCollisionPreset;
import fr.skytale.particleanimlib.animation.collision.SimpleCollisionProcessor;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class SphereWithInsideCollisionsPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereWithInsideCollisionsPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbCircles(8);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI / 4);
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
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
            return currentIterationBaseLocation.getWorld().getNearbyEntities(currentIterationBaseLocation, 10, 10, 10);
        });
        collisionBuilder.addPotentialCollidingTargetsFilter((entity, lineTask) -> !entity.getType().equals(EntityType.PLAYER));
        collisionBuilder.addCollisionProcessor(SimpleCollisionProcessor.useDefault(sphereBuilder, EntityCollisionPreset.TARGET_CENTER_INSIDE_SPHERE, (animationTask, target) -> {
            if(!(target instanceof LivingEntity)) return -1;
            ((LivingEntity) target).damage(1);
            return 20; // The entity can only take damages every 20 ticks.
        }));

        return collisionBuilder;
    }

}
