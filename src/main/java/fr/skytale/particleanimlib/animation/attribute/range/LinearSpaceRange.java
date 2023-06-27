package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

/**
 * A linear range of double
 * (the linear spacing means that the gap between two values is constant)
 */
public class LinearSpaceRange implements IRange<Double> {

    private final double start;
    private final double stop;
    private final int count;

    /**
     * Create a linear range of double
     *
     * @param start the first value
     * @param stop  the last value
     * @param count the number of value between start and stop (inclusive)
     */
    public LinearSpaceRange(double start, double stop, int count) {
        this.start = start;
        this.stop = stop;
        this.count = count;
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = new ArrayList<>();
        for (int n = 0; n < count; n++)
            v.add(start + ((stop - start) / count) * n);
        return v;
    }

}
