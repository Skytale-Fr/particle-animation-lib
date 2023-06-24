package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

public class StaticRange<T> implements IRange<T> {

    private final List<T> values;

    public StaticRange(List<T> values) {
        this.values = values;
    }

    @Override
    public List<T> generateValues() {
        return values;
    }
}
