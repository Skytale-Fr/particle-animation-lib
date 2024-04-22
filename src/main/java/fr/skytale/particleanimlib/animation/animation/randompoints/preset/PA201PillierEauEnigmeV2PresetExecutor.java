package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA201PillierEauEnigmeV2PresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public PA201PillierEauEnigmeV2PresetExecutor() {
        super(RandomPointsBuilder.class);
    }

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {
        randomPointsBuilder.setNbPoints(150);
        randomPointsBuilder.setRadius(50);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(10));
        randomPointsBuilder.setSpeed(new Constant<>(0.2d));
        randomPointsBuilder.setTicksDuration(20 * 30);
        randomPointsBuilder.setPointDefinition(new ParticleTemplate(Particle.DOLPHIN, 1, 0f, new Vector(0, 0, 0)));
    }
}
