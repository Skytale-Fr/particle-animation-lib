package fr.skytale.particleanimlib.animation.attribute.var.parent;

@FunctionalInterface
public interface ParametrizedCallbackWithPreviousValue<T> {
    T run(int iterationCount, T previousValue);
}
