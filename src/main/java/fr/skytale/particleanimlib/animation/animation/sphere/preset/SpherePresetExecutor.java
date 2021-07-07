package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbCircles(8);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI / 4);
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowFrequency(5);
    }
}
