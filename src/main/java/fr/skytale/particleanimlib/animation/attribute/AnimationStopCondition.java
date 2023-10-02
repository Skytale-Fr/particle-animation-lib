package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

@FunctionalInterface
public interface AnimationStopCondition {

    boolean canStop(AAnimationTask<?> animationTask);

}
