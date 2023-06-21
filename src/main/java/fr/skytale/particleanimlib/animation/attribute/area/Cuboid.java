package fr.skytale.particleanimlib.animation.attribute.area;

import org.bukkit.util.Vector;

public class Cuboid implements IArea {
    private final float width;
    private final float height;
    private final float depth;

    public Cuboid(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    @Override
    public boolean isInside(Vector vector) {
        return
                vector.getX() >= -width / 2 && vector.getX() <= width / 2 &&
                vector.getY() >= -height / 2 && vector.getY() <= height / 2 &&
                vector.getZ() >= -depth / 2 && vector.getZ() <= depth / 2;
    }

    public Cuboid(Cuboid cuboid) {
        this.width = cuboid.getWidth();
        this.height = cuboid.getHeight();
        this.depth = cuboid.getDepth();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }
}
