package fr.skytale.particleanimlib.animation.attribute.range;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * A range that returns a static list of 3 values in a Vector3D
 */
public class Composite3DRange implements IRange<Vector3D> {

    private final IRange<Double> first;
    private final IRange<Double> second;
    private final IRange<Double> third;

    /**
     * Creates a new Composite3DRange based on 3 ranges
     * @param first the first range that will define Vector2D.x
     * @param second the second range that will define Vector2D.y
     * @param third the third range that will define Vector2D.z
     */
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
