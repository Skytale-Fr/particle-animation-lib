package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class PA206ProtectionArcanique42PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA206ProtectionArcanique42PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        //General params
        sphereBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.WHITE));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setNbPoints(800);
        sphereBuilder.setRadius(8);

        //End of animation
        Sphere endSphere2 = new Sphere();
        for (int i = 0; i < 120; i++) {
            sphereBuilder.setPropagation(
                    Sphere.PropagationType.BOTTOM_TO_TOP,
                    0.1f,
                    0.1f);
            if (i > 0) {
                Sphere finalEndSphere = endSphere2;
                sphereBuilder.setAnimationEndedCallback(
                        animationEnding ->
                        {
                            finalEndSphere.setStopCondition(sphereBuilder.getAnimation().getStopCondition());
                            finalEndSphere.show();
                        }
                );
            }

            Sphere endSphere1 = sphereBuilder.getAnimation();

            sphereBuilder.setPropagation(
                    Sphere.PropagationType.TOP_TO_BOTTOM,
                    0.1f,
                    0.1f);
            sphereBuilder.setAnimationEndedCallback(
                    animationEnding -> {
                        endSphere1.setStopCondition(sphereBuilder.getAnimation().getStopCondition());
                        endSphere1.show();
                    });
            endSphere2 = sphereBuilder.getAnimation();
        }

        Sphere finalEndSphere2 = endSphere2;
        sphereBuilder.setAnimationEndedCallback(
                animationEnding ->
                {
                    finalEndSphere2.setStopCondition(sphereBuilder.getAnimation().getStopCondition());
                    finalEndSphere2.show();
                }
        );

    }
}
