package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

public class GeomspaceRange implements IRange<Double> {

    private double start;
    private double stop;

    private int count;

    private LogspaceRange logspace;

    public GeomspaceRange(double start, double max, int count) {
        this.start = start;
        this.stop = stop;
        this.count = count;
        this.logspace = new LogspaceRange(Math.log10(start), Math.log10(stop), count, 10.0);
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = logspace.generateValues();
        if (count > 0) v.set(0, start);
        return v;
    }

}
