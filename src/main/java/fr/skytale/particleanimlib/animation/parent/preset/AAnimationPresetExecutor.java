package fr.skytale.particleanimlib.animation.parent.preset;

import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;

import java.lang.reflect.InvocationTargetException;

public abstract class AAnimationPresetExecutor<T extends AAnimationBuilder<?>> {

    public static final String PRESET_NOT_COMPATIBLE = "The preset \"%s\" is not compatible with the builder type. It is only compatible with \"%s\".";

    protected final Class<T> builderClass;

    protected AAnimationPresetExecutor(Class<T> builderClass) {
        this.builderClass = builderClass;
    }

    protected abstract void apply(T builderCasted);

    public final void applyPreset(AAnimationBuilder<?> builder) {
        checkCompatibility(builder);
        T builderCasted = builderClass.cast(builder);
        apply(builderCasted);
    }

    public final boolean isCompatible(AAnimationBuilder<?> builder) {
        return this.builderClass.isInstance(builder);
    }

    public final void checkCompatibility(AAnimationBuilder<?> builder) {
        if (!isCompatible(builder))
            throw new IllegalArgumentException(String.format(PRESET_NOT_COMPATIBLE, getClass().getSimpleName(), builderClass.getSimpleName()));
    }

    public final Class<T> getBuilderClass() {
        return builderClass;
    }

    public final AAnimationBuilder<?> createBuilder() {
        try {
            AAnimationBuilder<?> builder = builderClass.getDeclaredConstructor().newInstance();
            applyPreset(builder);
            return builder;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    public final AAnimationBuilder<?> createEmptyBuilder() {
        try {
            return builderClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
