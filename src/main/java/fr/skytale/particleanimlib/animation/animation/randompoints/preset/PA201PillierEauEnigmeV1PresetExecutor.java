package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA201PillierEauEnigmeV1PresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public PA201PillierEauEnigmeV1PresetExecutor(){super(RandomPointsBuilder.class);}

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {
        randomPointsBuilder.setNbPoints(100);
        randomPointsBuilder.setRadius(50);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(10));
        randomPointsBuilder.setSpeed(new Constant<>(0.3f));
        randomPointsBuilder.setTicksDuration(20*30);
        randomPointsBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.SOUL_FIRE_FLAME, 0.01f));
        randomPointsBuilder.setShowPeriod(2);
    }
}
