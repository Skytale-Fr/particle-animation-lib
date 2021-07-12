package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class ElectricExplodingSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public ElectricExplodingSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(new CallbackVariable<>(iterationCount -> Math.sin(iterationCount / 2.0 + 2.0) / 4.0 + iterationCount / 20.0 + 0.15));
        sphereBuilder.setNbCircles(8);
        sphereBuilder.setAngleBetweenEachPoint(Math.toRadians(30));
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowFrequency(5);
    }
}
