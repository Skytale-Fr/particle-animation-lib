package fr.skytale.particleanimlib.animation.animation.randompoints.preset;

import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPoints;
import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsBuilder;
import fr.skytale.particleanimlib.animation.animation.randompoints.RandomPointsTask;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

import java.util.ArrayList;
import java.util.List;

public class PA207PillierEauActivationV1PresetExecutor extends AAnimationPresetExecutor<RandomPointsBuilder> {
    public PA207PillierEauActivationV1PresetExecutor() {
        super(RandomPointsBuilder.class);
    }

    @Override
    protected void apply(RandomPointsBuilder randomPointsBuilder, JavaPlugin plugin) {

        //Partie 3 - Points exploding
        randomPointsBuilder.applyPreset(AnimationPreset.PA_2_01_PILLIER_ENIGME_1, plugin);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(0));
        randomPointsBuilder.setSpeed(new DoublePeriodicallyEvolvingVariable(0.025d,0.08d));
        randomPointsBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.SOUL_FIRE_FLAME, 0.1f));
        randomPointsBuilder.setTicksDuration(20*5);
        RandomPoints p3 = randomPointsBuilder.getAnimation();

        //Partie 2 - Point converging to center
        randomPointsBuilder.applyPreset(AnimationPreset.PA_2_01_PILLIER_ENIGME_1, plugin);
        randomPointsBuilder.setDirectionChangePeriod(new Constant<>(0));
        randomPointsBuilder.setSpeed(new Constant<>(0.025d));
        randomPointsBuilder.setTicksDuration(20*4);
        randomPointsBuilder.setAnimationEndedCallback(task -> {
            RandomPointsTask randomPointsTask = (RandomPointsTask) task;
            List<RandomPointsTask.RandomPointData> randomPointsData = randomPointsTask.getRandomPointsData();

            for (RandomPointsTask.RandomPointData data : randomPointsData) {
                data.nextDirection = data.relativePosition.clone();
            }

            p3.show(true, randomPointsData);
        });
        RandomPoints p2 = randomPointsBuilder.getAnimation();

        //Partie 1 - Random points
        randomPointsBuilder.applyPreset(AnimationPreset.PA_2_01_PILLIER_ENIGME_1, plugin);
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
