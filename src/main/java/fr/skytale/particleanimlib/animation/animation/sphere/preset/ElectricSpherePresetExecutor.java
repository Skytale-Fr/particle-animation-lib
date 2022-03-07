package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class ElectricSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public ElectricSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        /*sphereBuilder.setRadius(2);//iterationCount -> Math.sin(iterationCount / 2.0 + 2.0) / 4.0 + iterationCount / 20.0 + 0.15));
        sphereBuilder.setNbCircles(8);*/
        sphereBuilder.setRadius(new CallbackVariable<>(iterationCount -> iterationCount%3==0 ? 0.7 : (iterationCount%3==1 ? 1.0 : 1.3)));//iterationCount -> Math.sin(iterationCount / 2.0 + 2.0) / 4.0 + iterationCount / 20.0 + 0.15));
        sphereBuilder.setNbCircles(new CallbackVariable<>(iterationCount -> iterationCount%3==0 ? 3 : (iterationCount%3==1 ? 5 : 3)));

        sphereBuilder.setAngleBetweenEachPoint(new CallbackVariable<>(iterationCount -> iterationCount%3==2 ? Math.toRadians(130) : Math.toRadians(30)));
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(244, 208, 63 ), null));
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(4);
    }
}
