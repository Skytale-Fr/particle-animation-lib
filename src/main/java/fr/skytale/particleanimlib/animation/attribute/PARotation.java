package fr.skytale.particleanimlib.animation.attribute;

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

    public PARotation(Vector directorU, Vector directorV) {
        Vector uNormalized = directorU.clone().normalize();
        Vector vNormalized = directorV.clone().normalize();
        this.rotation = new Rotation(
                new Vector3D(1, 0, 0),
                new Vector3D(0, 0, 1),
                new Vector3D(uNormalized.getX(), uNormalized.getY(), uNormalized.getZ()),
                new Vector3D(vNormalized.getX(), vNormalized.getY(), vNormalized.getZ())
        );
    }

    public PARotation(Rotation rotation) {
        this.rotation = new Rotation(rotation.getQ0(), rotation.getQ1(),rotation.getQ2(),rotation.getQ3(),false);
    }

    public PARotation() {
        this.rotation = Rotation.IDENTITY;
    }

    public PARotation(PARotation paRotation){
        this(paRotation.rotation);
    }

    public void rotate(Rotation additionalRotation) {
        this.rotation = additionalRotation.applyTo(this.rotation);
    }

    public void rotate(Vector axis, double radianAngle) {
        rotate(
                new Rotation(
                        new Vector3D(axis.getX(), axis.getY(), axis.getZ()),
                        radianAngle,
                        RotationConvention.VECTOR_OPERATOR)
        );
    }


    public Vector rotateVector(Vector originVector) {
        Vector3D originVector3D = new Vector3D(originVector.getX(), originVector.getY(), originVector.getZ());

        Vector3D rotatedVector3D = this.rotation.applyTo(originVector3D);

        return new Vector(
                rotatedVector3D.getX(),
                rotatedVector3D.getY(),
                rotatedVector3D.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PARotation that = (PARotation) o;

        return Rotation.distance(this.rotation, that.rotation) == 0;
    }

    @Override
    public int hashCode() {
        return rotation != null ? rotation.hashCode() : 0;
    }
}
