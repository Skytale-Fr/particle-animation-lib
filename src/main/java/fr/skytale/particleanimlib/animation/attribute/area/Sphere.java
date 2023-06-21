package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

public class Sphere implements IArea {
    private final float radius;

    public Sphere(float radius) {
        this.radius = radius;
    }

    public Sphere(Sphere sphere) {
        this.radius = sphere.getRadius();
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public boolean isInside(Vector vector) {
        return vector.lengthSquared() <= radius * radius;
    }
}
