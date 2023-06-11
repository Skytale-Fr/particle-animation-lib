package fr.skytale.particleanimlib.animation.animation.lightning.preset;

import fr.skytale.particleanimlib.animation.animation.lightning.LightningBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class LightningPresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningPresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder, JavaPlugin plugin) {
        final Location targetLocation = lightningBuilder.getOriginLocation();
        lightningBuilder.setTargetLocation(targetLocation);
        final Location lightningOrigin = targetLocation.clone();
        lightningOrigin.add(new Vector(0, 70, 0));
        lightningBuilder.setPosition(new LocatedAnimationPosition(lightningOrigin));
        lightningBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, new Color(255, 0, 0)));
        lightningBuilder.setDispersionAngle(Math.PI / 25);
        lightningBuilder.setDistanceBetweenPoints(1);
        lightningBuilder.setMaxDistanceBetweenLightningAngles(7);
        lightningBuilder.setMinDistanceBetweenLightningAngles(2);
        lightningBuilder.setTicksDuration(25);
        lightningBuilder.setShowPeriod(3);
        lightningBuilder.setConvergeToTarget(true);
    }
}
