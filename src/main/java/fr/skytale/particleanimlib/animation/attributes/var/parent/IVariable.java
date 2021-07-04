package fr.skytale.particleanimlib.animation.attributes.var.parent;

public interface IVariable<T> {
    /**
     * Retrieves the current value (constant or evolving according to the current iteration count)
     * @param iterationCount the current iterationCount
     * @return the value for this iteration count
     */
    T getCurrentValue(int iterationCount);
}
