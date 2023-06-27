package fr.skytale.particleanimlib.animation.attribute.curve;

import fr.skytale.particleanimlib.animation.attribute.range.IRange;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generate a list of values from a curve generator and a range
 * @param <T> the type of the range
 * @param <R> the type of the values that will be generated
 */
public class CurvePointsGenerator<T, R> extends CallbackVariable<List<R>> {

    /**
     * Builds a curve points generator
     * @param curveGenerator the curve generator
     * @param range the range of values to give to the curve generator
     */
    public CurvePointsGenerator(Function<T, R> curveGenerator, IRange<T> range) {
        this(curveGenerator, new Constant<>(range));
    }

    /**
     * Builds a curve points generator
     * @param curveGenerator the curve generator
     * @param range the range of values to give to the curve generator
     */
    public CurvePointsGenerator(Function<T, R> curveGenerator, IVariable<IRange<T>> range) {
        super(iterationCount -> range.getCurrentValue(iterationCount)
                .generateValues()
                .stream()
                .map(curveGenerator)
                .collect(Collectors.toList()));
    }

}
