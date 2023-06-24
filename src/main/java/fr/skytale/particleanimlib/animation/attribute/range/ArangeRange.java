package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

public class ArangeRange implements IRange<Double> {

    private final double start;
    private final double stop;
    private final double step;

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
