package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.line.Line;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineTask;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionBuilder;
import fr.skytale.particleanimlib.animation.collision.action.EntityCollisionActionCallbackPresets;
import fr.skytale.particleanimlib.animation.collision.processor.check.EntityCollisionCheckPreset;
import fr.skytale.particleanimlib.animation.collision.processor.ParticleCollisionProcessor;
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

public class HelicopterWithCollisionsPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    private static final int PROPELLER_COUNT = 3;
    private static final Orientation ORIENTATION = Orientation.UP; // The orientation of the circle handling all lines

    public HelicopterWithCollisionsPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());
        lineBuilder.setPosition(circleBuilder.getPosition());
        lineBuilder.setFromPositionToPoint1(new Vector(0, 6, 0));
        lineBuilder.setFromPositionToPoint2(new Vector(0, 0, 0));
        lineBuilder.setTicksDuration(1);
        lineBuilder.setShowPeriod(new Constant<>(2));
        lineBuilder.setNbPoints(new Constant<>(10));
        lineBuilder.addCollisionHandler(createCollisionBuilder(lineBuilder).build());

        Line line = lineBuilder.getAnimation();
        circleBuilder.setNbPoints(5, true);
        circleBuilder.setTicksDuration(20 * 20);
        circleBuilder.setShowPeriod(3);
        circleBuilder.setRadius(0.1);
        circleBuilder.setRotation(new Vector(0, 1, 0), new DoublePeriodicallyEvolvingVariable(0d, Math.PI / 1000, 3));
        circleBuilder.setPointDefinition(new SubAnimPointDefinition(line, new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION)));
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
                        new ParticleTemplate(ParticleEffect.EXPLOSION_HUGE),
                        AViewers.fromNearbyPlayers(50),
                        1
                )
        ));

        return collisionBuilder;
    }

}
