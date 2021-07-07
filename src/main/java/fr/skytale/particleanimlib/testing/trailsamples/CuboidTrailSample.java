package fr.skytale.particleanimlib.testing.trailsamples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.TrailTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.time.Duration;

public class CuboidTrailSample implements IPTrailAnimSample {
    @Override
    public TrailTask getTrailTask(JavaPlugin plugin) {
        CuboidBuilder cuboidBuilder = new CuboidBuilder();
        cuboidBuilder.setAxis(new Vector(0, 1, 0));
        cuboidBuilder.setAxisChangeFrequency(0);
        cuboidBuilder.setStepAngleAlpha(Math.toRadians(3));
        cuboidBuilder.setStepAngleAlphaMax(Math.toRadians(30));
        cuboidBuilder.setStepAngleAlphaChangeFactor(2);
        cuboidBuilder.setStepAngleAlphaChangeFrequency(0);
        cuboidBuilder.setFromLocationToFirstCorner(new Vector(-1, -1, -1));
        cuboidBuilder.setFromLocationToSecondCorner(new Vector(1, 1, 1));
        cuboidBuilder.setRelativeLocation(new Vector(0, 0.75, 0));
        cuboidBuilder.setStep(0.2);

        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(100);
        cuboidBuilder.setShowFrequency(5);
        cuboidBuilder.setJavaPlugin(plugin);

        TrailBuilder trailBuilder = new TrailBuilder();
        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckFrequency(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(cuboidBuilder.getAnimation());

        return trailBuilder.getTrail().start();
    }

    @Override
    public String getName() {
        return "cuboid";
    }
}
