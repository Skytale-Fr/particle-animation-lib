package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

public class PA107PortailApparitionPartie1V5PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA107PortailApparitionPartie1V5PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {

        sphereBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_V4, plugin);
        Sphere originalAnimation = sphereBuilder.getAnimation();

        sphereBuilder.setRadius(0.1);
        sphereBuilder.setNbPoints(1);
        sphereBuilder.setTicksDuration(20 * 2);
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0f));

        //3rd repetition
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
        });
        Sphere circle_3 = sphereBuilder.getAnimation();

        //2nd repetition
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_3.show();
        });
        Sphere circle_2 = sphereBuilder.getAnimation();

        //1rst repetition
        sphereBuilder.setTicksDuration(1);
        sphereBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_2.show();
        });
    }
}
