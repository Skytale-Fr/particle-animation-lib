package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

/**
 * A range of values that are spaced logarithmically.
 * (the logarithmically spacing means that the gap between two values decrease as you move through the range)
 */
public class LogSpaceRange implements IRange<Double> {

    private final double base;
    private final LinearSpaceRange linearSpaceRange;

    /**
     * Create a logarithmic range of double
     * (the logarithmically spacing means that the gap between two values decrease as you move through the range)
     * @param start the first value
     * @param stop the last value
     * @param count the number of value between start and stop (inclusive)
     */
    public LogSpaceRange(double start, double stop, int count) {
        this(start, stop, count, 10.0);
    }

    /**
     * Create a logarithmic range of double
     * (the logarithmically spacing means that the gap between two values decrease as you move through the range)
     * @param start the first value
     * @param stop the last value
     * @param count the number of value between start and stop (inclusive)
     * @param base the base of the logarithm
     */
    public LogSpaceRange(double start, double stop, int count, double base) {
        this(start, stop, count, base, true);
    }


    /**
     * Create a logarithmic range of double
     * (the logarithmically spacing means that the gap between two values decrease as you move through the range)
     * @param start the first value
     * @param stop the last value
     * @param count the number of value between start and stop (inclusive)
     * @param base the base of the logarithm
     * @param normalized if true, the final start and stop values are the not affected by the logarithm and stays the same as the input
     */
    public LogSpaceRange(double start, double stop, int count, double base, boolean normalized) {
        this.base = base;
        this.linearSpaceRange = new LinearSpaceRange(normalized ? logA(start, base) : start, normalized ? logA(stop, base) : stop, count);
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = linearSpaceRange.generateValues();
        for (int i = 0; i < v.size(); i++)
            v.set(i, Math.pow(base, v.get(i)));
        return v;
    }

    public static Double logA(Double x, Double base) {
        return Math.log10(x) / Math.log10(base);
    }

}
