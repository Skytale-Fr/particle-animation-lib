package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

@FunctionalInterface
public interface AnimationEndedCallback {
    void run(AAnimationTask<?> task);
}
