package fr.skytale.particleanimlib.animation.attribute.range;

import java.util.List;

/**
 * A range of values (usually numbers)
 * @param <T> the type of the values (usually numbers)
 */
public interface IRange<T> {

    List<T> generateValues();

}
