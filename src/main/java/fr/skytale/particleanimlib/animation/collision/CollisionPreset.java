package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public class CollisionPreset<T> {

    private CollisionPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionPredicate;

    public CollisionPreset(CollisionPredicate<T, ? extends AAnimationTask<? extends AAnimation>> collisionPredicate) {
        this.collisionPredicate = collisionPredicate;
    }

    public <K extends AAnimationTask<? extends AAnimation>> CollisionPredicate<T, K> getCollisionPredicate() {
        return (CollisionPredicate<T, K>) collisionPredicate;
    }

}
