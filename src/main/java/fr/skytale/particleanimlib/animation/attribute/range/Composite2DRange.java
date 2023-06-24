package fr.skytale.particleanimlib.animation.attribute.range;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class Composite2DRange implements IRange<Vector2D> {

    private IRange<Double> first;
    private IRange<Double> second;

    public Composite2DRange(IRange<Double> first, IRange<Double> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public List<Vector2D> generateValues() {
        return null;
    }

}
