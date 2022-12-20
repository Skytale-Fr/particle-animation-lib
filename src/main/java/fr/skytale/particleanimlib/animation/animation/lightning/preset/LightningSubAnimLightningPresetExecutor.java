package fr.skytale.particleanimlib.animation.animation.lightning.preset;

import fr.skytale.particleanimlib.animation.animation.lightning.LightningBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
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
        subLightningBuilder.setPosition(lightningBuilder.getPosition().copy());
        subLightningBuilder.setJavaPlugin(lightningBuilder.getJavaPlugin());
        subLightningBuilder.setTicksDuration(1);
        subLightningBuilder.setMaxDistanceBetweenLightningAngles(40);

        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        lightningBuilder.setPointDefinition(new SubAnimPointDefinition(subLightningBuilder.getAnimation()));
        lightningBuilder.setTicksDuration(1);
    }
}
