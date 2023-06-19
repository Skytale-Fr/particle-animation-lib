package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

public class PA201PillierEauEnigmePresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public PA201PillierEauEnigmePresetExecutor(){super(RandomPointsBuilder.class);}

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {
        randomPointsBuilder.setNbPoints(150);
        randomPointsBuilder.setRadius(50);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(10));
        randomPointsBuilder.setSpeed(new Constant<>(0.3f));
        randomPointsBuilder.setTicksDuration(20*30);
        randomPointsBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.01f));
        randomPointsBuilder.setShowPeriod(2);
    }
}
