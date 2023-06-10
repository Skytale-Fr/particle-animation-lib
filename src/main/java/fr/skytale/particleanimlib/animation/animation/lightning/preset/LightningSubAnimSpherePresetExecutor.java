package fr.skytale.particleanimlib.animation.animation.lightning.preset;

import fr.skytale.particleanimlib.animation.animation.lightning.LightningBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
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
        subAnimSphereBuilder.setPosition(lightningBuilder.getPosition().copy());
        subAnimSphereBuilder.setJavaPlugin(lightningBuilder.getJavaPlugin());
        subAnimSphereBuilder.setNbPoints(20);
        subAnimSphereBuilder.setRadius(2);
        subAnimSphereBuilder.setTicksDuration(1);

        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        lightningBuilder.setPointDefinition(new SubAnimPointDefinition(subAnimSphereBuilder.getAnimation()));
    }
}
