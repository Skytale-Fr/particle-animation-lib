package fr.skytale.particleanimlib.animation.attribute;

import org.bukkit.util.Vector;

public enum Orientation {
    NORTH(new Vector(-1, 0, 0), new Vector(0, 1, 0)),
    SOUTH(new Vector(1, 0, 0), new Vector(0, 1, 0)),
    EAST(new Vector(0, 0, -1), new Vector(0, 1, 0)),
    WEST(new Vector(0, 0, 1), new Vector(0, 1, 0)),
    UP(new Vector(0, 0, 1), new Vector(1, 0, 0)),
    DOWN(new Vector(0, 0, 1), new Vector(-1, 0, 0))
    ;

    private final Vector u;
    private final Vector v;

    Orientation(Vector u, Vector v) {
        this.u = u;
        this.v = v;
    }

    public Vector getU(double length) {
        return u.clone().multiply(length);
    }

    public Vector getV(double length) {
        return v.clone().multiply(length);
    }
}
