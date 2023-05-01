package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class ElectricSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public ElectricSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        sphereBuilder.setRadius(new CallbackVariable<>(iterationCount -> iterationCount % 3 == 0 ? 1.0 : (
                iterationCount % 3 == 1 ? 1.3 : 1.6
        )));
        sphereBuilder.setNbPoints(new CallbackVariable<>(iterationCount ->
                iterationCount % 3 == 0 ? 20 : (iterationCount % 3 == 1 ? 30 : 40)));
//        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, 1, 0.01f, new Vector(0,0,0), Color.WHITE));
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.03f, new Vector(0, 0, 0), (ParticleData) null));
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(4);
    }

//
}
