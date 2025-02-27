package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA202LiseusePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA202LiseusePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
                new CallbackVariable<>(iterationCount -> (Math.PI / 48) * Math.pow(-1, (iterationCount / 60)))
        );
        circleBuilder.setNbPoints(4, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(20 * 10);
        circleBuilder.setShowPeriod(2);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 0.05f));
    }
}
