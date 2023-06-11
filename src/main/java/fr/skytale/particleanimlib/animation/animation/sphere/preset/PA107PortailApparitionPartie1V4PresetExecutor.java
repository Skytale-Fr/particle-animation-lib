package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class PA107PortailApparitionPartie1V4PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA107PortailApparitionPartie1V4PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {

        double startRadius = 20d;
        double endRadius = 4d;
        int tickDuration = 20*5;

        sphereBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(0, 0, 1),
                new VectorPeriodicallyEvolvingVariable(new Vector(-1,1,1), new Vector(9,-8,-1)),
                Math.PI/24);

        sphereBuilder.setNbPoints((int) (startRadius/2));

        double radiusChangeValue = (endRadius-startRadius)/tickDuration;
        sphereBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(startRadius,  radiusChangeValue));

        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.05f));
        sphereBuilder.setTicksDuration(tickDuration);
    }
}
