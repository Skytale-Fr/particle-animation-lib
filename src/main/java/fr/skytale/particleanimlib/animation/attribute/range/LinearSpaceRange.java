package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

public class LinearSpaceRange implements IRange<Double> {

    private final double start;
    private final double stop;
    private final int count;

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
