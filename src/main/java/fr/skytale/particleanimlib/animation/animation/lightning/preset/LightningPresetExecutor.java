package fr.skytale.particleanimlib.animation.animation.lightning.preset;

import fr.skytale.particleanimlib.animation.animation.lightning.LightningBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class LightningPresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningPresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder, JavaPlugin plugin) {
        final Location targetLocation = ((AAnimationPosition) lightningBuilder.getPosition()).copy().getCurrentValue(0).getAfterMoveLocation();
        lightningBuilder.setTargetLocation(targetLocation);
        lightningBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 0, 0)));
        lightningBuilder.setDispersionAngle(Math.PI / 8);
        lightningBuilder.setDistanceBetweenPoints(0.7);
        lightningBuilder.setMaxDistanceBetweenLightningAngles(20);
        lightningBuilder.setMinDistanceBetweenLightningAngles(5);
        lightningBuilder.setTicksDuration(25);
        lightningBuilder.setShowPeriod(3);
        lightningBuilder.setConvergeToTarget(true);
    }
}
