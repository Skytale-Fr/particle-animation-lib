package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class PropagatingDownSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PropagatingDownSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbPoints(200);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setPropagation(
                Sphere.PropagationType.TOP_TO_BOTTOM,
                0.2f,
                0.05f);
        sphereBuilder.setTicksDuration(200);
        sphereBuilder.setShowPeriod(2);
    }
}
