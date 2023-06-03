package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public interface AnimationEndedCallback {
    <T extends AAnimation> void run(T animationEnding, AAnimationTask<T> task);
}
