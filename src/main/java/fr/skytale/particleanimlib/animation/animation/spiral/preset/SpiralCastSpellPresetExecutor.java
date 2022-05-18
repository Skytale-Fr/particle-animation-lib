package fr.skytale.particleanimlib.animation.animation.spiral.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.animation.spiral.SpiralTask;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class SpiralCastSpellPresetExecutor extends AAnimationPresetExecutor<SpiralBuilder> {

    public SpiralCastSpellPresetExecutor() {
        super(SpiralBuilder.class);
    }

    @Override
    protected void apply(SpiralBuilder spiralBuilder, JavaPlugin plugin) {
        spiralBuilder.applyPreset(AnimationPreset.SPIRAL, plugin);
        spiralBuilder.setTicksDuration(60 * 20);
        Entity targetEntity = spiralBuilder.getAnimation().getDirection().getTargetEntity();
        if (targetEntity != null) {
            spiralBuilder.setStopCondition(animationTask -> {
                Location iterationBaseLocation = animationTask.getCurrentAbsoluteLocation();
                return targetEntity.getLocation().distance(iterationBaseLocation) < 0.5;
            });

            SphereBuilder sphereBuilder = new SphereBuilder();
            sphereBuilder.setPosition(APosition.fromEntity(targetEntity));
            sphereBuilder.setJavaPlugin(spiralBuilder.getJavaPlugin());
            sphereBuilder.applyPreset(AnimationPreset.SPHERE_ELECTRIC, plugin);
            sphereBuilder.setTicksDuration(20);
            sphereBuilder.setShowPeriod(1);

            spiralBuilder.addAnimationEndedCallback(animationEnding -> {
                sphereBuilder.getAnimation().show();
            });
        }
    }
}
