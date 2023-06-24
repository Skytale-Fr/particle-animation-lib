package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.ArrayList;
import java.util.List;

public class LogspaceRange implements IRange<Double> {

    private double start;
    private double stop;
    private int count;
    private double base;

    private LinspaceRange linspace;

    public LogspaceRange(double start, double stop, int count, double base) {
        this.start = start;
        this.stop = stop;
        this.count = count;
        this.base = base;
        this.linspace = new LinspaceRange(start, stop, count);
    }

    @Override
    public List<Double> generateValues() {
        List<Double> v = linspace.generateValues();
        for (int i = 0; i < v.size(); i++)
            v.set(i, Math.pow(base, v.get(i)));
        return v;
    }

}
