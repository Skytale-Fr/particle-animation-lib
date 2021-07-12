package fr.skytale.particleanimlib.animation.animation.polygon.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SimplePolygonPresetExecutor extends AAnimationPresetExecutor<PolygonBuilder> {

    public SimplePolygonPresetExecutor() {
        super(PolygonBuilder.class);
    }

    @Override
    protected void apply(PolygonBuilder polygonBuilder, JavaPlugin plugin) {
        polygonBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        polygonBuilder.setNbVertices(8);
        polygonBuilder.setDistanceBetweenPoints(0.3);
        polygonBuilder.setDistanceFromCenterToVertices(5);
        polygonBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        polygonBuilder.setTicksDuration(100);
        polygonBuilder.setShowFrequency(1);
    }
}