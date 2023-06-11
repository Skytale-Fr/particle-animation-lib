package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class PA107PortailApparitionPartie1V2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPartie1V2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_V1, plugin);
        Circle originalAnimation = circleBuilder.getAnimation();

        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(20 * 2);

        //3rd repetition
        circleBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
        });
        Circle circle_3 = circleBuilder.getAnimation();

        //2nd repetition
        circleBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_3.show();
        });
        Circle circle_2 = circleBuilder.getAnimation();

        //1rst repetition
        circleBuilder.setTicksDuration(1);
        circleBuilder.setAnimationEndedCallback(task -> {
            originalAnimation.show();
            circle_2.show();
        });
    }
}
