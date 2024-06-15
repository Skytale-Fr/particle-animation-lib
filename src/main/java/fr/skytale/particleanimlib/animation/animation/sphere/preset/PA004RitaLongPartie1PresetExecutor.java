package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA004RitaLongPartie1PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA004RitaLongPartie1PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        double startRadius = 15;
        double middleRadius = 4;

        //Start of animation
        sphereBuilder.setNbPoints(20);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(20 * 8);
        sphereBuilder.setShowPeriod(0);
        sphereBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(startRadius, (middleRadius - startRadius) / (20 * 8)));
        sphereBuilder.setRotation(new VectorPeriodicallyEvolvingVariable(new Vector(-1, 1, 1), new Vector(9, -8, -1)), Math.PI / 12);
        sphereBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 1, 0.01f, new Vector(0.3, 0.3, 0.3)));
    }
}
