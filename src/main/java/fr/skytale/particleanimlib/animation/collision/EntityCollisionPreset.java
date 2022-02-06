package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class EntityCollisionPreset<K extends AAnimationTask<? extends AAnimation>> extends CollisionPreset<Entity> {

    public static final EntityCollisionPreset<? extends AAnimationTask> EXACT_BOUNDING_BOX = new EntityCollisionPreset<>((location, animationTask, target) -> {
        BoundingBox entityBoundingBox = target.getBoundingBox();
        return entityBoundingBox.contains(location.getX(), location.getY(), location.getZ());
    });

    public EntityCollisionPreset(CollisionPredicate<Entity, K> collisionPredicate) {
        super(collisionPredicate);
    }

}
