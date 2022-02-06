package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.function.BiFunction;

public class ParticleCollisionProcessor<T, K extends AAnimationTask<? extends AAnimation>> extends CollisionProcessor<T, K> {

    public ParticleCollisionProcessor(CollisionPredicate<T, K> collisionTest, CollisionActionCallback<T, K> actionCallback) {
        super(CollisionTestType.PER_PARTICLE, collisionTest, actionCallback);
    }

    public static <T, K extends AAnimationTask<? extends AAnimation>> ParticleCollisionProcessor<T, K> useDefault(AAnimationBuilder<?, K> builder, CollisionPreset<T> preset, CollisionActionCallback<T, K> actionCallback) {
        return new ParticleCollisionProcessor<T, K>(preset.<K>getCollisionPredicate(), actionCallback);
    }

}
