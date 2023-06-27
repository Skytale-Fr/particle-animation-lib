package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

/**
 * Represent an area whatever the shape
 */
public interface IArea {
    public abstract boolean isInside(Vector vector);
}
