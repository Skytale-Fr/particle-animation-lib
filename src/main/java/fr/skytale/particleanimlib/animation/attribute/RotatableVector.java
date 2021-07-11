package fr.skytale.particleanimlib.animation.attribute;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class RotatableVector extends Vector {

    public static class Plane2D {
        public Vector u;
        public Vector v;

        public Plane2D(Vector u, Vector v) {
            this.u = u;
            this.v = v;
        }
    }

    public RotatableVector(int x, int y, int z) {
        super(x, y, z);
    }

    public RotatableVector(Vector v) {
        super(v.getX(), v.getY(), v.getZ());
    }

    public RotatableVector(double x, double y, double z) {
        super(x, y, z);
    }

    public RotatableVector(float x, float y, float z) {
        super(x, y, z);
    }

    public @NotNull Vector rotateAroundNonUnitAxis(Vector axis, double angle) throws IllegalArgumentException {
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

    public @NotNull Vector rotateAroundAxis(Vector axis, double angle) throws IllegalArgumentException {
        Preconditions.checkArgument(axis != null, "The provided axis vector was null");
        return rotateAroundNonUnitAxis(axis.clone().normalize(), angle);
    }

    public Vector3D toVector3D() {
        return new Vector3D(getX(), getY(), getZ());
    }

    public Plane2D getPlane(Location locInThePlane) {
        Vector3D pointOfPlane = new RotatableVector(locInThePlane.toVector()).toVector3D();
        Plane plane = new Plane(pointOfPlane, toVector3D(), 0.001);
        return new Plane2D(new RotatableVector(plane.getU().getX(), plane.getU().getY(), plane.getU().getZ()), new RotatableVector(plane.getV().getX(), plane.getV().getY(), plane.getV().getZ()));
    }

    public Plane2D getPlane() {
        Plane plane = new Plane(toVector3D(), 0.001);
        return new Plane2D(new RotatableVector(plane.getU().getX(), plane.getU().getY(), plane.getU().getZ()), new RotatableVector(plane.getV().getX(), plane.getV().getY(), plane.getV().getZ()));
    }

}