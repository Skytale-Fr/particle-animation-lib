package fr.skytale.particleanimlib.animation.parent.preset;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public abstract class APresetInitializer {

    public static Set<Class<? extends APresetInitializer>> alreadyInitializedSet = new HashSet<>();

    public static void initialize(Class<? extends APresetInitializer> initializerClass) {
        if (!alreadyInitializedSet.contains(initializerClass)) {
            try {
                APresetInitializer initializer = initializerClass.getDeclaredConstructor().newInstance();
                initializer.initialize();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    protected APresetInitializer() {
    }

    protected abstract void initialize();
}
