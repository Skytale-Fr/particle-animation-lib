package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class PropagatingUpSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PropagatingUpSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbPoints(200);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setPropagation(
                Sphere.PropagationType.BOTTOM_TO_TOP,
                0.2f,
                0.05f);
        sphereBuilder.setTicksDuration(200);
        sphereBuilder.setShowPeriod(2);
    }
}
