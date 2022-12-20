package fr.skytale.particleanimlib.trail.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.trailposition.StaticTrailPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.time.Duration;

public class CloudTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {

        //Circle used in trail
        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setRadius(new Constant<>(0.8));
        circleBuilder.setNbPoints(new Constant<>(8), true);
        circleBuilder.setPosition(new StaticTrailPosition(true));
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.CLOUD));
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setJavaPlugin(plugin);

        //Trail builder
        trailBuilder.setDuration(Duration.ofSeconds(120));
        trailBuilder.setCheckPeriod(0);
        trailBuilder.setMinPlayerToAnimationDistance(0);
        trailBuilder.setMinDistanceBetweenAnimations(2.0f);
        trailBuilder.addAnimation(circleBuilder.getAnimation());

        //First circle change to make inner circle
        circleBuilder.setRadius(0.25);
        circleBuilder.setNbPoints(new Constant<>(3), true);
        circleBuilder.setPosition(new StaticTrailPosition(true));

        trailBuilder.addAnimation(circleBuilder.getAnimation());
    }
}
