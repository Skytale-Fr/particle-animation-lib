package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomPointsPresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public RandomPointsPresetExecutor(){super(RandomPointsBuilder.class);}

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {
        randomPointsBuilder.setNbPoints(20);
        randomPointsBuilder.setRadius(5);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(20));
        randomPointsBuilder.setSpeed(new Constant<>(0.1d));
        randomPointsBuilder.setTicksDuration(20*10);
    }
}
