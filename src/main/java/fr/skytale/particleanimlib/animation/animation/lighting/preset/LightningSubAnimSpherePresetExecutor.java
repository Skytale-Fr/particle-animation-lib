package fr.skytale.particleanimlib.animation.animation.lighting.preset;

import fr.skytale.particleanimlib.animation.animation.lighting.LightningBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class LightningSubAnimSpherePresetExecutor extends AAnimationPresetExecutor<LightningBuilder> {

    public LightningSubAnimSpherePresetExecutor() {
        super(LightningBuilder.class);
    }

    @Override
    protected void apply(LightningBuilder lightningBuilder) {
        SphereBuilder subAnimSphereBuilder = new SphereBuilder();
        subAnimSphereBuilder.applyPreset(AnimationPreset.SPHERE);
        subAnimSphereBuilder.setPosition(lightningBuilder.getPosition().clone());
        subAnimSphereBuilder.setJavaPlugin(lightningBuilder.getJavaPlugin());

        lightningBuilder.applyPreset(AnimationPreset.LIGHTNING);
        lightningBuilder.setPointDefinition(PointDefinition.fromSubAnim(subAnimSphereBuilder.getAnimation()));
        lightningBuilder.setTicksDuration(1);
    }
}
