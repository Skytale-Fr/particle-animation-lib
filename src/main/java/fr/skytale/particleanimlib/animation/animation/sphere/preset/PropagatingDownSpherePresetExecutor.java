package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class PropagatingDownSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PropagatingDownSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbCircles(20);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI / 4);
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setPropagation(Sphere.PropagationType.TOP_TO_BOTTOM, 2);
        sphereBuilder.setTicksDuration(200);
        sphereBuilder.setShowPeriod(5);
    }
}
