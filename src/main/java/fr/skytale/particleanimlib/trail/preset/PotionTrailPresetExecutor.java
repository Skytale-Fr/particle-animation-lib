package fr.skytale.particleanimlib.trail.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.Duration;

public class PotionTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {

        //Circle used in trail
        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setRadius(new Constant<>(0.8));
        circleBuilder.setNbPoints(new Constant<>(8), true);
        circleBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0, 0, 0))));
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        circleBuilder.setMainParticle(new ParticleTemplate("SPELL_MOB", null, null,1,1f));
        circleBuilder.setTicksDuration(1);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setJavaPlugin(plugin);

        //Trail builder
        trailBuilder.setDuration(Duration.ofSeconds(120));
        trailBuilder.setCheckPeriod(0);
        trailBuilder.setMinPlayerToAnimationDistance(0);
        trailBuilder.setMinDistanceBetweenAnimations(2.0f);
        trailBuilder.addAnimation(circleBuilder.getAnimation(true));

        //First circle change to make inner circle
        circleBuilder.setRadius(0.25);
        circleBuilder.setNbPoints(new Constant<>(3), true);
        circleBuilder.setPosition(APosition.fromTrail(new Constant<>(new Vector(0, 0.2f, 0))));

        trailBuilder.addAnimation(circleBuilder.getAnimation(true));
    }
}
