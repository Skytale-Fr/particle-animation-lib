package fr.skytale.particleanimlib.trail.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.time.Duration;

public class CircleTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {

        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setRadius(new Constant<>(2.0));
        circleBuilder.setNbPoints(new Constant<>(10), true);
        circleBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0, 0, 0))));
        circleBuilder.setDirectorVectors(new Constant<>(new Vector(1, 0, 0)), new Constant<>(new Vector(0, 0, 1)));

        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(80);
        circleBuilder.setShowFrequency(new Constant<>(5));
        circleBuilder.setJavaPlugin(plugin);

        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckFrequency(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(circleBuilder.getAnimation(true));
    }
}
