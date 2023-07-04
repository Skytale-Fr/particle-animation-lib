package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

import java.util.function.Predicate;

/**
 * Represent an area whatever the shape
 */
public class CallbackArea implements IArea {
    private final Predicate<Vector> isInsidePredicate;

    /**
     * Builds an area from a predicate
     * @param isInsidePredicate the predicate that will be used to check if a vector, from animation position to the
     *                         position of the point, is inside the area
     */
    public CallbackArea(Predicate<Vector> isInsidePredicate) {
        this.isInsidePredicate = isInsidePredicate;
    }

    public CallbackArea(CallbackArea callbackArea) {
        this.isInsidePredicate = callbackArea.getIsInsidePredicate();
    }

    public Predicate<Vector> getIsInsidePredicate() {
        return isInsidePredicate;
    }

    @Override
    public boolean isInside(Vector vector) {
        return isInsidePredicate.test(vector);
    }
}
