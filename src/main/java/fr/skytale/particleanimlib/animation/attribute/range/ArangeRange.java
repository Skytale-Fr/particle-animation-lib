package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

/**
 * A range similar to LinearRange but based on a step instead of a number of values
 */
public class ArangeRange implements IRange<Double> {

    private final double start;
    private final double stop;
    private final double step;

    /**
     * Creates a new ArangeRange
     * @param start the start value
     * @param stop the stop value (exclusive)
     * @param step the step between two values
     */
    public ArangeRange(double start, double stop, double step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = new ArrayList<>();
        for (double d = start; d < stop; d += step) v.add(d);
        return v;
    }

}
