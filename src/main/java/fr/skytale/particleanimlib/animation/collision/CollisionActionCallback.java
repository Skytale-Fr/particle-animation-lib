package fr.skytale.particleanimlib.animation.collision;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

@FunctionalInterface
public interface CollisionActionCallback<T, K extends AAnimationTask> {

    public int run(K animationTask, T target);

}
