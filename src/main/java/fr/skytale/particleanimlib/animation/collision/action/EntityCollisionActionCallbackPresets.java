package fr.skytale.particleanimlib.animation.collision.action;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityCollisionActionCallbackPresets {
    //prevents instantiation
    private EntityCollisionActionCallbackPresets() {
    }

    public static <K extends AAnimationTask<?>> CollisionActionCallback<Entity, K> displayParticle(ParticleTemplate particleTemplate, AViewers viewers, int ticksPeriodBetweenDisplay) {
        return (animationTask, target) -> {
                    if (!(target instanceof LivingEntity)) return -1;
                    final LivingEntity livingEntity = (LivingEntity) target;
                    if (livingEntity.isDead() || !livingEntity.isValid()) return -1;
                    particleTemplate
                            .location(target.getLocation())
                            .receivers(viewers.getPlayers(target.getLocation()));
                    return ticksPeriodBetweenDisplay; // The particle can only be displayed every 10 ticks.
                };
    }

    public static <K extends AAnimationTask<?>> CollisionActionCallback<Entity, K> damageEntity(float damageAmount, int ticksPeriodBetweenDamage) {
        return (animationTask, target) -> {
            if ((!(target instanceof LivingEntity))) return -1;
            final LivingEntity livingEntity = (LivingEntity) target;
            if (livingEntity.isDead() || !livingEntity.isValid()) return -1;

            livingEntity.damage(damageAmount);

            return ticksPeriodBetweenDamage; // The entity can only take damages every ticksPeriodBetweenDamage ticks.
        };
    }
}
