package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

@FunctionalInterface
public interface AnimationStopCondition<T extends AAnimationTask<?>> {

    public boolean canStop(T animationTask);

}
