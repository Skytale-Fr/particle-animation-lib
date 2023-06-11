package fr.skytale.particleanimlib.trail.preset.circle;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.trailposition.StaticTrailPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.Duration;

public class CircleOrientedTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {

        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setJavaPlugin(plugin);
        circleBuilder.setNbPoints(20, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(80);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setPosition(new StaticTrailPosition(
                true,
                true,
                new Vector(0, 0.9, 0),
                null
        ));

        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckPeriod(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(circleBuilder.getAnimation());
    }
}
