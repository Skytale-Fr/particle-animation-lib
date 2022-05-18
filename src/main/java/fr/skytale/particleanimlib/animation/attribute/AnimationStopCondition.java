package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

@FunctionalInterface
public interface AnimationStopCondition {

    public boolean canStop(AAnimationTask<?> animationTask);

}
