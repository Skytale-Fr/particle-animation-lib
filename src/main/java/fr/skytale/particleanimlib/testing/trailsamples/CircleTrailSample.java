package fr.skytale.particleanimlib.testing.trailsamples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.TrailTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.time.Duration;

public class CircleTrailSample implements IPTrailAnimSample {
    @Override
    public TrailTask getTrailTask(JavaPlugin plugin) {

        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setRadius(2.0);
        circleBuilder.setNbPoints(20);
        circleBuilder.setRelativeLocation(new Vector(0, 0, 0));
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setMoveStepVector(new Vector(0,0.2,0));
        circleBuilder.setMoveFrequency(1);

        circleBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        circleBuilder.setTicksDuration(400);
        circleBuilder.setShowFrequency(5);
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
