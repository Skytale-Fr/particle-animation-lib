package fr.skytale.particleanimlib.animation.attributes;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import java.util.logging.Level;

public class PARotation {

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
        this.rotation = rotation;
    }

    public PARotation() {
        this.rotation = Rotation.IDENTITY;
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

}
