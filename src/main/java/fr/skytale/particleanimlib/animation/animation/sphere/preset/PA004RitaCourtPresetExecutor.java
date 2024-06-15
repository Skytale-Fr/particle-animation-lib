package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA004RitaCourtPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA004RitaCourtPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        double middleRadius = 4;

        //End of animation
        SphereBuilder sphereBuilderEnd = new SphereBuilder();
        sphereBuilderEnd.setJavaPlugin(plugin);
        sphereBuilderEnd.setPosition(sphereBuilder.getPosition());
        sphereBuilderEnd.setNbPoints(60);
        sphereBuilderEnd.setSphereType(Sphere.Type.FULL);
        sphereBuilderEnd.setTicksDuration(20*1);
        sphereBuilderEnd.setShowPeriod(0);
        sphereBuilderEnd.setRotation(new Vector(0,1,0), Math.PI/24);
        sphereBuilderEnd.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 1, 0.5f, new Vector(0.3,0.3,0.3)));
        sphereBuilderEnd.setRadius(
                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount-4)+4)
        );

        //Middle of animation
        SphereBuilder sphereBuilderMiddle = new SphereBuilder();
        sphereBuilderMiddle.setJavaPlugin(plugin);
        sphereBuilderMiddle.setPosition(sphereBuilder.getPosition());
        sphereBuilderMiddle.setNbPoints(20);
        sphereBuilderMiddle.setSphereType(Sphere.Type.FULL);
        sphereBuilderMiddle.setTicksDuration(20*2);
        sphereBuilderMiddle.setShowPeriod(0);
        sphereBuilderMiddle.setRadius(middleRadius);
        sphereBuilderMiddle.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 1, 0.03f, new Vector(0.1,0.1,0.1)));
        sphereBuilderMiddle.setRotation(new Vector(0,1,0), Math.PI/24);
        sphereBuilderMiddle.setAnimationEndedCallback(animationEnding -> sphereBuilderEnd.getAnimation().show());

        sphereBuilder.setTicksDuration(1);
        sphereBuilder.setNbPoints(1);
        sphereBuilder.setRadius(0.1);
        sphereBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 0f));
        sphereBuilder.setAnimationEndedCallback(animationEnding -> sphereBuilderMiddle.getAnimation().show());
    }
}
