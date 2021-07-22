package fr.skytale.particleanimlib.animation.animation.obj;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Segment {

    private Vector3D a;
    private Vector3D b;
    private List<Face> relatedFaces;
    public Segment(Vector3D a, Vector3D b, Face face) {
        this.a = a;
        this.b = b;
        this.relatedFaces = new ArrayList<>();
        this.relatedFaces.add(face);
    }

    public Vector3D getA() {
        return a;
    }

    public void setA(Vector3D a) {
        this.a = a;
    }

    public Vector3D getB() {
        return b;
    }

    public void setB(Vector3D b) {
        this.b = b;
    }

    public List<Face> getRelatedFaces() {
        return relatedFaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return (a.equals(segment.a) && b.equals(segment.b))
                || (a.equals(segment.b) && b.equals(segment.a));
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b) + Objects.hash(b, a);
    }

    public static class Face {
        public List<Vector3D> vertices;
        public Vector3D planeNormal;
    }
}
