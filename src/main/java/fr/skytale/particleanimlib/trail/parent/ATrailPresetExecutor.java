package fr.skytale.particleanimlib.trail.parent;

import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public abstract class ATrailPresetExecutor {

    public abstract void applyPreset(TrailBuilder builder, JavaPlugin plugin);

    public TrailBuilder createBuilder(JavaPlugin plugin) {
        TrailBuilder builder = new TrailBuilder();
        applyPreset(builder, plugin);
        return builder;
    }
}
