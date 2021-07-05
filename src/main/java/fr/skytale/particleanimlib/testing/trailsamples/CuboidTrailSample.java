package fr.skytale.particleanimlib.testing.trailsamples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
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
        cuboidBuilder.setFromLocationToFirstCorner(new Constant<>(new Vector(-1, -1, -1)));
        cuboidBuilder.setFromLocationToSecondCorner(new Constant<>(new Vector(1, 1, 1)));
        cuboidBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0, 0.75, 0))));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.2));

        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(100);
        cuboidBuilder.setShowFrequency(new Constant<>(5));
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
