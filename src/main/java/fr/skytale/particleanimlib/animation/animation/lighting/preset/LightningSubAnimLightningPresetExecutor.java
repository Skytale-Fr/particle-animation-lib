package fr.skytale.particleanimlib.animation.animation.lighting.preset;

import fr.skytale.particleanimlib.animation.animation.lighting.LightningBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class LightningSubAnimLightningPresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningSubAnimLightningPresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder, JavaPlugin plugin) {
        LightningBuilder subLightningBuilder = new LightningBuilder();
        subLightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        subLightningBuilder.setPosition(lightningBuilder.getPosition().clone());
        subLightningBuilder.setJavaPlugin(lightningBuilder.getJavaPlugin());
        subLightningBuilder.setTicksDuration(1);
        subLightningBuilder.setMaxDistance(40);

        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        lightningBuilder.setPointDefinition(PointDefinition.fromSubAnim(subLightningBuilder.getAnimation()));
        lightningBuilder.setTicksDuration(1);
    }
}
