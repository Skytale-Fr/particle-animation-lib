package fr.skytale.particleanimlib.animation.parent.animation.subanim;

import fr.skytale.particleanimlib.animation.attribute.position.APosition;

public interface ISubAnimation extends Cloneable {
    void setPosition(APosition position);

    APosition getPosition();

    void show();

    ISubAnimation clone();
}