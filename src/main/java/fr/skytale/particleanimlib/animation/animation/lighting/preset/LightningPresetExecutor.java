package fr.skytale.particleanimlib.animation.animation.lighting.preset;

import fr.skytale.particleanimlib.animation.animation.lighting.LightningBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class LightningPresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningPresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder) {
        lightningBuilder.setDirection(AnimationDirection.fromMoveVector(new Vector(0, 0.1, 0)));
        lightningBuilder.setPointDefinition(new ParticleTemplate("REDSTONE", new Color(255, 0, 0), null));
        lightningBuilder.setDispersionAngle(Math.PI / 8);
        lightningBuilder.setDistanceBetweenPoints(0.7);
        lightningBuilder.setMaxDistanceBetweenLightingAngles(20);
        lightningBuilder.setMinDistanceBetweenLightingAngles(5);
        lightningBuilder.setMaxDistance(75);
        lightningBuilder.setTicksDuration(25);
        lightningBuilder.setShowFrequency(3);
        lightningBuilder.setConvergeToTarget(true);
    }
}
