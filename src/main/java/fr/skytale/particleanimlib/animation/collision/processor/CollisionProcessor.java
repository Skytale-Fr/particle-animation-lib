package fr.skytale.particleanimlib.animation.collision.processor;

import fr.skytale.particleanimlib.animation.collision.action.CollisionActionCallback;
import fr.skytale.particleanimlib.animation.collision.processor.check.CollisionCheckPredicate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * A collision processor should handle the type of the collision process, the collision test predicate and the callback.
 *
 * @param <T> The type of target you want to perform collisions on
 * @param <K> The type of animation task you want to plug this collision processor to
 */
public class CollisionProcessor<T, K extends AAnimationTask> {

    /**
     * The type of the collision process. You may see CollisionTestType.
     *
     * @see CollisionTestType
     */
    protected CollisionTestType collisionTestType;
    /**
     * The predicate that should check the collision.
     */
    protected CollisionCheckPredicate<T, K> collisionTest;
    /**
     * The action callback that will be called if the predicate is verified.
     * This function should return an integer represents how many ticks a target can't be part of the collision process
     * since the last time it collides.
     */
    protected CollisionActionCallback<T, K> actionCallback;

    /**
     * @param collisionTestType The type of the collision process. You may see CollisionTestType.
     * @param collisionTest     The predicate that should check the collision.
     * @param actionCallback    The action callback that will be called if the predicate is verified. This function should return an integer represents how many ticks a target can't be part of the collision process since the last time it collides.
     */
    public CollisionProcessor(CollisionTestType collisionTestType, CollisionCheckPredicate<T, K> collisionTest, CollisionActionCallback<T, K> actionCallback) {
        this.collisionTestType = collisionTestType;
        this.collisionTest = collisionTest;
        this.actionCallback = actionCallback;
    }

    /**
     * Gets the collision test type.
     *
     * @return The collision test type
     */
    public CollisionTestType getCollisionTestType() {
        return collisionTestType;
    }

    /**
     * Gets the collision check predicate.
     *
     * @return The collision check predicate
     */
    public CollisionCheckPredicate<T, K> getCollisionTest() {
        return collisionTest;
    }

    /**
     * Gets the collision action callback.
     *
     * @return The collision action callback
     */
    public CollisionActionCallback<T, K> getActionCallback() {
        return actionCallback;
    }

}
