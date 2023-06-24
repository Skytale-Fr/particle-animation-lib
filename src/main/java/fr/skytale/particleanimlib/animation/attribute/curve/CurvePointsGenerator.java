package fr.skytale.particleanimlib.animation.attribute.curve;

import fr.skytale.particleanimlib.animation.attribute.range.IRange;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurvePointsGenerator<T, R> extends CallbackVariable<List<R>> {

    public CurvePointsGenerator(Function<T, R> curveGenerator, IRange<T> range) {
        this(curveGenerator, new Constant<>(range));
    }

    public CurvePointsGenerator(Function<T, R> curveGenerator, IVariable<IRange<T>> range) {
        super(iterationCount -> range.getCurrentValue(iterationCount)
                .generateValues()
                .stream()
                .map(curveGenerator)
                .collect(Collectors.toList()));
    }

}
