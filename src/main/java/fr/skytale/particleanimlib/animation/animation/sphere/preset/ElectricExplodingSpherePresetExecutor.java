package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class ElectricExplodingSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public ElectricExplodingSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(new CallbackVariable<>(iterationCount -> Math.sin(iterationCount / 2.0 + 2.0) / 4.0 +
                                                                         iterationCount / 20.0 + 0.15));
        sphereBuilder.setNbPoints(80);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(5);
    }
}
