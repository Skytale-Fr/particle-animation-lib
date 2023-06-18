package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class PA107PortailPartie1PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA107PortailPartie1PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        //  1.2
        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setPosition(sphereBuilder.getPosition());
        circleBuilder.setJavaPlugin(plugin);
        circleBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_2, plugin);
        Circle p1_2 = circleBuilder.getAnimation();

        //  1.1
        sphereBuilder.applyPreset(AnimationPreset.PA_1_07_PORTAIL_APPARITION_PARTIE_1_1,plugin);
        Sphere p1_1 = sphereBuilder.getAnimation();

        sphereBuilder.setRadius(0.1);
        sphereBuilder.setNbPoints(1);
        sphereBuilder.setTicksDuration(1);
        sphereBuilder.setAnimationEndedCallback(task -> {
            p1_1.show();    //10 sec
            //p1_2.show();
        });
    }
}
