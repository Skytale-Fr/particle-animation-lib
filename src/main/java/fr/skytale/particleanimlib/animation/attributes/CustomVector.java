package fr.skytale.particleanimlib.animation.attributes;

import com.google.common.base.Preconditions;
import org.bukkit.util.Vector;

public class CustomVector extends Vector {

    public CustomVector(int x, int y, int z) {
        super(x, y, z);
    }

    public CustomVector(Vector v) {
        super(v.getX(), v.getY(), v.getZ());
    }

    public CustomVector(double x, double y, double z) {
        super(x, y, z);
    }

    public CustomVector(float x, float y, float z) {
        super(x, y, z);
    }

    public Vector rotateAroundNonUnitAxis(Vector axis, double angle) throws IllegalArgumentException {
        Preconditions.checkArgument(axis != null, "The provided axis vector was null");
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        double x2 = axis.getX();
        double y2 = axis.getY();
        double z2 = axis.getZ();
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        double dotProduct = super.dot(axis);
        double xPrime = x2 * dotProduct * (1.0D - cosTheta) + x * cosTheta + (-z2 * y + y2 * z) * sinTheta;
        double yPrime = y2 * dotProduct * (1.0D - cosTheta) + y * cosTheta + (z2 * x - x2 * z) * sinTheta;
        double zPrime = z2 * dotProduct * (1.0D - cosTheta) + z * cosTheta + (-y2 * x + x2 * y) * sinTheta;
        return super.setX(xPrime).setY(yPrime).setZ(zPrime);
    }

    public Vector rotateAroundAxis(Vector axis, double angle) throws IllegalArgumentException {
        Preconditions.checkArgument(axis != null, "The provided axis vector was null");
        return rotateAroundNonUnitAxis(axis.clone().normalize(), angle);
    }

}