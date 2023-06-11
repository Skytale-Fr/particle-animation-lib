package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.util.Vector;

public class PARotation {
    public static final PARotation DEFAULT_ROTATION = new PARotation();
    private Rotation rotation;

    public PARotation(Vector axis, double radianAngle) {

        this.rotation = Rotation.IDENTITY;
        rotate(axis, radianAngle);
    }

    public PARotation(Vector fromU, Vector newU, Vector fromV, Vector newV) {
        if (fromU.length() == 0 || newU.length() == 0 || fromV.length() == 0 || newV.length() == 0) {
            this.rotation = Rotation.IDENTITY;
        } else {
            this.rotation = new Rotation(
                    new Vector3D(fromU.getX(), fromU.getY(), fromU.getZ()).normalize(),
                    new Vector3D(fromV.getX(), fromV.getY(), fromV.getZ()).normalize(),
                    new Vector3D(newU.getX(), newU.getY(), newU.getZ()).normalize(),
                    new Vector3D(newV.getX(), newV.getY(), newV.getZ()).normalize()
            );
        }
    }

    public PARotation(Vector direction) {
        this(AAnimationTask.W, direction);
    }

    public PARotation(Vector from, Vector to) {
        if (from.length() == 0 || to.length() == 0) {
            this.rotation = Rotation.IDENTITY;
        } else {
            this.rotation = new Rotation(
                    new Vector3D(from.getX(), from.getY(), from.getZ()).normalize(),
                    new Vector3D(to.getX(), to.getY(), to.getZ()).normalize()
            );
        }
    }

    public PARotation(Rotation rotation) {
        this.rotation = new Rotation(rotation.getQ0(), rotation.getQ1(), rotation.getQ2(), rotation.getQ3(), false);
    }

    public PARotation() {
        this.rotation = Rotation.IDENTITY;
    }

    public PARotation(PARotation paRotation) {
        this(paRotation.rotation);
    }

    public void rotate(PARotation additionalRotation) {
        rotate(additionalRotation.rotation);
    }

    public void rotate(Vector axis, double radianAngle) {
        rotate(
                new Rotation(
                        new Vector3D(axis.getX(), axis.getY(), axis.getZ()),
                        radianAngle,
                        RotationConvention.VECTOR_OPERATOR)
        );
    }

    private void rotate(Rotation additionalRotation) {
        this.rotation = additionalRotation.applyTo(this.rotation);
    }

    public Vector rotateVector(Vector originVector) {
        Vector3D originVector3D = new Vector3D(originVector.getX(), originVector.getY(), originVector.getZ());

        Vector3D rotatedVector3D = this.rotation.applyTo(originVector3D);

        return new Vector(
                rotatedVector3D.getX(),
                rotatedVector3D.getY(),
                rotatedVector3D.getZ());
    }

    public Vector getAxis() {
        Vector3D axis = this.rotation.getAxis(RotationConvention.VECTOR_OPERATOR);
        return new Vector(axis.getX(), axis.getY(), axis.getZ());
    }

    @Override
    public int hashCode() {
        return rotation != null ? rotation.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PARotation that = (PARotation) o;

        return Rotation.distance(this.rotation, that.rotation) == 0;
    }
}
