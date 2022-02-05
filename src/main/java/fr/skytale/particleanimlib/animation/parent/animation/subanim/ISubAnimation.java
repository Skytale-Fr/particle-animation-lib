package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

public interface ISubAnimation extends Cloneable {
    APosition getPosition();

    void setPosition(APosition position);

    AAnimationTask<? extends AAnimation> show();

    ISubAnimation clone();
}