package fr.skytale.particleanimlib.animation.attribute.position.parent;

import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;

public interface IPosition {

    /**
     * Retrieves the type of the position
     *
     * @return the type of the position
     */
    PositionType getType();

    IPosition copy();
}
