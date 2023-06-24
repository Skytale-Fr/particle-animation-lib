package fr.skytale.particleanimlib.animation.attribute.range;

import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;

import java.util.ArrayList;
import java.util.List;

public class EvolvingRange<T> extends CallbackVariable<IRange<T>> {

    public EvolvingRange(IRange<T> range, int nbSimultaneousPoint) {
        this(range.generateValues(), nbSimultaneousPoint);
        //this avoid regenerating values constantly
    }

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
