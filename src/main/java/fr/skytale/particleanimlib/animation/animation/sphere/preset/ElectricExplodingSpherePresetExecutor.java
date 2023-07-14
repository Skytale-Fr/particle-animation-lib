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
        // see https://www.desmos.com/calculator/uozineavsu
        final double s = 9;
        final double gap = 0.1;
        final double p = 1.62;
        final double n = 1.5;
        sphereBuilder.setRadius(new CallbackVariable<>(iterationCount -> {
            double x = iterationCount * 0.05;
            double t = x * s;
            return (Math.exp(x * Math.sin(t) / (t * gap)) - 1 + x * p) / n;
        }));

        sphereBuilder.setNbPoints(80);
        sphereBuilder.setTicksDuration(120);
        sphereBuilder.setShowPeriod(2);
    }
}
