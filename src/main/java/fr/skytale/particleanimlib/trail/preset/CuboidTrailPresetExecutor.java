package fr.skytale.particleanimlib.trail.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.time.Duration;

public class CuboidTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {
        CuboidBuilder cuboidBuilder = new CuboidBuilder();
        cuboidBuilder.setFromLocationToFirstCorner(new Constant<>(new Vector(-0.5, -0.5, -0.5)));
        cuboidBuilder.setFromLocationToSecondCorner(new Constant<>(new Vector(0.5, 0.5, 0.5)));
        cuboidBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0, 0.75, 0))));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.2));
        cuboidBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.toRadians(3), Math.toRadians(1), 1));

        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(100);
        cuboidBuilder.setShowPeriod(new Constant<>(5));
        cuboidBuilder.setJavaPlugin(plugin);

        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckPeriod(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(cuboidBuilder.getAnimation(true));
    }
}
