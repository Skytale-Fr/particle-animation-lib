package fr.skytale.particleanimlib.animation.collision;

import com.google.common.collect.Sets;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;

public interface EntityFilters {

    /**
     * Creates a BiPredicate for CollisionBuilder#addPotentialCollidingTargetsFilter
     * to check if an entity is one of the given types.
     *
     * @param types Entity types
     * @return
     */
    public static BiPredicate<Entity, AAnimationTask<?>> isType(EntityType... types) {
        return isType(Sets.newHashSet(types));
    }

    /**
     * Creates a BiPredicate for CollisionBuilder#addPotentialCollidingTargetsFilter
     * to check if an entity is one of the given types.
     *
     * @param types Entity types
     * @return
     */
    public static BiPredicate<Entity, AAnimationTask<?>> isType(Collection<EntityType> types) {
        Set<EntityType> entityTypes;
        if (!(types instanceof HashSet)) entityTypes = Sets.newHashSet(types);
        else entityTypes = (HashSet<EntityType>) types;
        return (entity, animationTask) -> {
            EntityType entityType = entity.getType();
            return entityTypes.contains(entityType);
        };
    }

    /**
     * Creates a BiPredicate for CollisionBuilder#addPotentialCollidingTargetsFilter
     * to check if an entity isn't one of the given types.
     *
     * @param types Entity types
     * @return
     */
    public static <K extends AAnimationTask<?>> BiPredicate<Entity, K> isNotType(EntityType... types) {
        return isNotType(Sets.newHashSet(types));
    }

    /**
     * Creates a BiPredicate for CollisionBuilder#addPotentialCollidingTargetsFilter
     * to check if an entity isn't one of the given types.
     *
     * @param types Entity types
     * @return
     */
    public static <K extends AAnimationTask<?>> BiPredicate<Entity, K> isNotType(Collection<EntityType> types) {
        Set<EntityType> entityTypes;
        if (!(types instanceof HashSet)) entityTypes = Sets.newHashSet(types);
        else entityTypes = (HashSet<EntityType>) types;
        return (entity, animationTask) -> {
            EntityType entityType = entity.getType();
            return !entityTypes.contains(entityType);
        };
    }

}
