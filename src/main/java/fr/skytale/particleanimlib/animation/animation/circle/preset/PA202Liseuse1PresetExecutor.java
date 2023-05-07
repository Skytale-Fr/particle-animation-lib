package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class PA202Liseuse1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA202Liseuse1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setNbPoints(4, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(20*10);
        circleBuilder.setShowPeriod(2);
        circleBuilder.setRotation(
                new Vector(0,1,0),
                new CallbackVariable<>(iterationCount -> (Math.PI/48) * Math.pow(-1,(iterationCount/60)) )
        );
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FIREWORKS_SPARK, 0.05f));
    }
}
