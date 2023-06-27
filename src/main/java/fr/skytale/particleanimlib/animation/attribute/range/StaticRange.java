package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

/**
 * A range that returns a static list of values
 *
 * @param <T> the type of the values
 */
public class StaticRange<T> implements IRange<T> {

    private final List<T> values;

    /**
     * Creates a new static range
     * @param values the values of the range
     */
    public StaticRange(List<T> values) {
        this.values = values;
    }

    @Override
    public List<T> generateValues() {
        return values;
    }
}
