package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA101MagieFumee1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA101MagieFumee1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        CircleBuilder fumee = new CircleBuilder();
        fumee.setPosition(circleBuilder.getPosition());
        fumee.applyPreset(AnimationPreset.PA_0_01_FUMEE, plugin);

        CircleBuilder magie = new CircleBuilder();
        magie.setPosition(circleBuilder.getPosition());
        magie.setJavaPlugin(circleBuilder.getJavaPlugin());
        magie.setRadius(0.1);
        magie.setNbPoints(3);
        magie.setShowPeriod(20);
        magie.setTicksDuration(20);
        magie.setPointDefinition(new ParticleTemplate(Particle.SPELL_WITCH, 100, 0.1f, new Vector(2, 2, 2)));
        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowPeriod(0);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL));
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            fumee.getAnimation().show();
            magie.getAnimation().show();
        });
    }
}