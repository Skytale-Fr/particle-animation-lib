package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA004RitaCourtPartie1PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA004RitaCourtPartie1PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        double middleRadius = 4;

        sphereBuilder.setNbPoints(20);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(20 * 2);
        sphereBuilder.setShowPeriod(0);
        sphereBuilder.setRadius(middleRadius);
        sphereBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 1, 0.03f, new Vector(0.1, 0.1, 0.1)));
        sphereBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 24);
    }
}
