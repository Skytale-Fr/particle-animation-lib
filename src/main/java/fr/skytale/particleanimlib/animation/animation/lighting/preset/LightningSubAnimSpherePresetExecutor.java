package fr.skytale.particleanimlib.animation.animation.lighting.preset;

import fr.skytale.particleanimlib.animation.animation.lighting.LightningBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class LightningSubAnimSpherePresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningSubAnimSpherePresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder, JavaPlugin plugin) {
        SphereBuilder subAnimSphereBuilder = new SphereBuilder();
        subAnimSphereBuilder.applyPreset(AnimationPreset.SPHERE, plugin);
        subAnimSphereBuilder.setPosition(lightningBuilder.getPosition().clone());
        subAnimSphereBuilder.setJavaPlugin(lightningBuilder.getJavaPlugin());

        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        lightningBuilder.setPointDefinition(PointDefinition.fromSubAnim(subAnimSphereBuilder.getAnimation()));
        lightningBuilder.setTicksDuration(1);
    }
}
