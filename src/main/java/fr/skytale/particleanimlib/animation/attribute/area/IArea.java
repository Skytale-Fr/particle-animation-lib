package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

/**
 * Represent an area whatever the shape
 */

@FunctionalInterface
public interface IArea {
    boolean isInside(Vector vector);
}
