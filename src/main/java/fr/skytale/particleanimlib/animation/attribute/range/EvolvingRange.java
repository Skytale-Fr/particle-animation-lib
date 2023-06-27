package fr.skytale.particleanimlib.animation.attribute.range;

import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * A range that evolves over time
 * @param <T> the type of the values
 */
public class EvolvingRange<T> extends CallbackVariable<IRange<T>> {

    /**
     * Creates a new evolving range
     * @param range the range to evolve
     * @param nbSimultaneousPoint the number of simultaneous point to display from the given range
     */
    public EvolvingRange(IRange<T> range, int nbSimultaneousPoint) {
        this(range.generateValues(), nbSimultaneousPoint);
        //this avoid regenerating values constantly
    }

    /**
     * Creates a new evolving range
     * @param values the values of the range
     * @param nbSimultaneousPoint the number of simultaneous point to display from the given range
     */
    public EvolvingRange(List<T> values, int nbSimultaneousPoint) {
        super(iterationCount -> {
            if (iterationCount >= values.size()) {
                return new StaticRange<>(new ArrayList<>());
            } else {
                return new StaticRange<T>(
                        values.subList(
                                iterationCount,
                                Math.min(iterationCount + nbSimultaneousPoint + 1, values.size())
                        )
                );
            }
        });
    }
}
