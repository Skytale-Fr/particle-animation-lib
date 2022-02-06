package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.function.BiFunction;

public class CollisionProcessor<T, K extends AAnimationTask> {

    protected CollisionTestType collisionTestType;
    protected CollisionPredicate<T, K> collisionTest;
    protected CollisionActionCallback<T, K> actionCallback;

    public CollisionProcessor(CollisionTestType collisionTestType, CollisionPredicate<T, K> collisionTest, CollisionActionCallback<T, K> actionCallback) {
        this.collisionTestType = collisionTestType;
        this.collisionTest = collisionTest;
        this.actionCallback = actionCallback;
    }

    public CollisionTestType getCollisionTestType() {
        return collisionTestType;
    }

    public CollisionPredicate<T, K> getCollisionTest() {
        return collisionTest;
    }

    public CollisionActionCallback<T, K> getActionCallback() {
        return actionCallback;
    }

}
