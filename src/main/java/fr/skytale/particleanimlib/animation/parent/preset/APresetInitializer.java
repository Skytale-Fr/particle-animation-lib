package fr.skytale.particleanimlib.animation.parent.preset;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public abstract class APresetInitializer {

    public static Set<Class<? extends APresetInitializer>> alreadyInitializedSet = new HashSet<>();

    protected APresetInitializer() {
    }

    public static void initialize(Class<? extends APresetInitializer> initializerClass, JavaPlugin plugin) {
        if (!alreadyInitializedSet.contains(initializerClass)) {
            try {
                APresetInitializer initializer = initializerClass.getDeclaredConstructor().newInstance();
                initializer.initialize(plugin);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    protected abstract void initialize(JavaPlugin plugin);
}
