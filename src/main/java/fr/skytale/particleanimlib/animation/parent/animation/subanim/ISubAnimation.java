package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;

public interface ISubAnimation extends Cloneable {
    APosition getPosition();

    void setPosition(APosition position);

    void show();

    ISubAnimation clone();
}