package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class CallbackArea implements IArea {
    private final Predicate<Vector> isInsidePredicate;

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
