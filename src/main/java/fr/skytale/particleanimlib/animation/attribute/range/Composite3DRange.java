package fr.skytale.particleanimlib.animation.attribute.range;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class Composite3DRange implements IRange<Vector3D> {

    private IRange<Double> first;
    private IRange<Double> second;
    private IRange<Double> third;

    public Composite3DRange(IRange<Double> first, IRange<Double> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public List<Vector3D> generateValues() {
        return null;
    }

}
