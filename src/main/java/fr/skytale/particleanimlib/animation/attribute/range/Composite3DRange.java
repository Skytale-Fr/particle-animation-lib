package fr.skytale.particleanimlib.animation.attribute.range;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Composite3DRange implements IRange<Vector3D> {

    private final IRange<Double> first;
    private final IRange<Double> second;
    private final IRange<Double> third;

    public Composite3DRange(IRange<Double> first, IRange<Double> second, IRange<Double> third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public List<Vector3D> generateValues() {
        List<Double> firstValues = first.generateValues();
        List<Double> secondValues = second.generateValues();
        List<Double> thirdValues = third.generateValues();

        int minSize = Math.min(Math.min(firstValues.size(), secondValues.size()), thirdValues.size());

        List<Vector3D> values = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            values.add(new Vector3D(firstValues.get(i), secondValues.get(i), thirdValues.get(i)));
        }
        return values;
    }

}
