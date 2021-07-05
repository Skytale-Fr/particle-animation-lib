package fr.skytale.particleanimlib.testing.trailsamples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.position.LocationPosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.LocationEquationEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.TrailTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.time.Duration;

public class CircleTrailSample implements IPTrailAnimSample {
    @Override
    public TrailTask getTrailTask(JavaPlugin plugin) {

        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setRadius(new Constant<>(2.0));
        circleBuilder.setNbPoints(new Constant<>(10));
        circleBuilder.setPosition(APosition.fromTrail(new VectorPeriodicallyEvolvingVariable(new Vector(0, 0, 0), new Vector(0, 0.2, 0), 1)));
        circleBuilder.setDirectorVectors(new Constant<>(new Vector(1, 0, 0)), new Constant<>(new Vector(0, 0, 1)));

        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(80);
        circleBuilder.setShowFrequency(new Constant<>(5));
        circleBuilder.setJavaPlugin(plugin);

        TrailBuilder trailBuilder = new TrailBuilder();
        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckFrequency(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(circleBuilder.getAnimation());

        return trailBuilder.getTrail().start();
    }

    @Override
    public String getName() {
        return "circle";
    }
}
