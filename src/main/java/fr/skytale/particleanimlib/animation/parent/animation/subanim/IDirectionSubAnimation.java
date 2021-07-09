package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;

public interface IDirectionSubAnimation extends ISubAnimation {
    AnimationDirection getDirection();
    void setDirection(AnimationDirection direction);
}
