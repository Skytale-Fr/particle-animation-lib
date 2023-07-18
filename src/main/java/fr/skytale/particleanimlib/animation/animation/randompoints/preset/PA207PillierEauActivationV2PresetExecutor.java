package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPoints;
import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsTask;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PA207PillierEauActivationV2PresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public PA207PillierEauActivationV2PresetExecutor() {
        super(RandomPointsBuilder.class);
    }

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {
        //Partie 2
        randomPointsBuilder.applyPreset(AnimationPreset.PA_2_01_PILLIER_ENIGME_2, plugin);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(0));
        randomPointsBuilder.setSpeed(new Constant<>(0.025d));
        RandomPoints p2 = randomPointsBuilder.getAnimation();

        //Partie 1
        randomPointsBuilder.applyPreset(AnimationPreset.PA_2_01_PILLIER_ENIGME_2, plugin);
        randomPointsBuilder.setAnimationEndedCallback(task -> {
            RandomPointsTask randomPointsTask = (RandomPointsTask) task;
            List<RandomPointsTask.RandomPointData> randomPointsData = randomPointsTask.getRandomPointsData();

            for (RandomPointsTask.RandomPointData data : randomPointsData) {
                data.nextDirection = data.relativePosition.clone().multiply(-1);
            }

            p2.show(true, randomPointsData);
        });

    }
}
