package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA004RitaCourtPartie2PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA004RitaCourtPartie2PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setNbPoints(60);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(20);
        sphereBuilder.setShowPeriod(0);
        sphereBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 24);
        sphereBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 1, 0.5f, new Vector(0.3, 0.3, 0.3)));
        sphereBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount - 4) + 4)
        );
    }
}
