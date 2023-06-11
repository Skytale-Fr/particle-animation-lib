package fr.skytale.particleanimlib.animation.animation.lightning.preset;

import fr.skytale.particleanimlib.animation.animation.lightning.Lightning;
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
        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING, plugin);
        lightningBuilder.setTicksDuration(1);
        lightningBuilder.setConvergeToTarget(false);
        Lightning subLightning = lightningBuilder.getAnimation();

        lightningBuilder.setPointDefinition(new SubAnimPointDefinition(subLightning));
        lightningBuilder.setTicksDuration(25);
        lightningBuilder.setConvergeToTarget(true);
        lightningBuilder.setDistanceBetweenPoints(20);
    }
}
