package fr.skytale.particleanimlib.animation.attribute;

import org.bukkit.util.Vector;

public enum Orientation {
    NORTH(new Vector(-1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1)),
    SOUTH(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)),
    EAST(new Vector(0, 0, -1), new Vector(0, 1, 0), new Vector(1, 0, 0)),
    WEST(new Vector(0, 0, 1), new Vector(0, 1, 0), new Vector(-1, 0, 0)),
    UP(new Vector(0, 0, 1), new Vector(1, 0, 0), new Vector(0, 1, 0)),
    DOWN(new Vector(0, 0, 1), new Vector(-1, 0, 0), new Vector(0, -1, 0))
    ;

    private final Vector u;
    private final Vector v;
    private final Vector direction;

    Orientation(Vector u, Vector v, Vector direction) {
        this.u = u;
        this.v = v;
        this.direction = direction;
    }

    public Vector getU(double length) {
        return u.clone().multiply(length);
    }

    public Vector getV(double length) {
        return v.clone().multiply(length);
    }

    // I don't know if it's relevant to ask for a length for the direction attribute ?
    public Vector getDirection(double length) { return direction.clone().multiply(length); }
}
