package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

public class LogSpaceRange implements IRange<Double> {

    private final double base;
    private final LinearSpaceRange linearSpaceRange;

    public LogSpaceRange(double start, double stop, int count) {
        this(start, stop, count, 10.0);
    }

    public LogSpaceRange(double start, double stop, int count, double base) {
        this(start, stop, count, base, true);
    }

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
