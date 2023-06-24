package fr.skytale.particleanimlib.animation.attribute.range;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Composite2DRange implements IRange<Vector2D> {

    private final IRange<Double> first;
    private final IRange<Double> second;

    public Composite2DRange(IRange<Double> first, IRange<Double> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public List<Vector2D> generateValues() {
        List<Double> firstValues = first.generateValues();
        List<Double> secondValues = second.generateValues();

        int minSize = Math.min(firstValues.size(), secondValues.size());

        List<Vector2D> values = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            values.add(new Vector2D(firstValues.get(i), secondValues.get(i)));
        }
        return values;
    }

}
