package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA003BrumeEpaisse1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA003BrumeEpaisse1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setShowPeriod(5);
        circleBuilder.setTicksDuration(5 * 20);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.CLOUD, 500, 0.01f, new Vector(5, 5, 5)));
    }
}