package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

/**
 * A range of values that are spaced geometrically.
 * (the geometrically spacing means that the gap between two values increases as you move through the range)
 */
public class GeomSpaceRange implements IRange<Double> {

    private final int count;
    private final double start;
    private final LogSpaceRange logSpaceRange;

    /**
     * Create a geometrically spaced range of double
     * (the geometrically spacing means that the gap between two values increases as you move through the range)
     * @param start the first value
     * @param stop the last value
     * @param count the number of value between start and stop (inclusive)
     */
    public GeomSpaceRange(double start, double stop, int count) {
        this.count = count;
        this.start = start;
        this.logSpaceRange = new LogSpaceRange(Math.log10(start), Math.log10(stop), count, 10.0, false);
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = logSpaceRange.generateValues();
        if (count > 0) v.set(0, start);
        return v;
    }

}
