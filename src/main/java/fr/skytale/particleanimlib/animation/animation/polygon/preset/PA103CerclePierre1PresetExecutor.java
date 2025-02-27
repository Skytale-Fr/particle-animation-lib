package fr.skytale.particleanimlib.animation.animation.polygon.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.Polygon;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA103CerclePierre1PresetExecutor extends AAnimationPresetExecutor<PolygonBuilder> {

    public PA103CerclePierre1PresetExecutor() {
        super(PolygonBuilder.class);
    }

    @Override
    protected void apply(PolygonBuilder polygonBuilder, JavaPlugin plugin) {
        double radius = 10d;

        AnimationPreset.POLYGON.apply(polygonBuilder, plugin);
        polygonBuilder.setNbVertices(5);
        polygonBuilder.setDistanceFromCenterToVertices(radius);
        polygonBuilder.setPointDefinition(new ParticleTemplate(Particle.DRAGON_BREATH, 0.005f));
        polygonBuilder.setTicksDuration(20 * 10);
        polygonBuilder.setRotation(
                new Vector(0, 1, 0),
                Math.PI / 100
        );
        polygonBuilder.setShowPeriod(10);
        Polygon outerPolygon = polygonBuilder.getAnimation();

        //Cercle
        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setJavaPlugin(plugin);
        circleBuilder.setPosition(polygonBuilder.getPosition());
        circleBuilder.setRadius(new CallbackVariable<>(iterationCount -> (radius / 2) * Math.cos(iterationCount / 10d) + (radius / 2)));
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.DRAGON_BREATH, 0.01f));
        circleBuilder.setTicksDuration(20 * 10);
        Circle circle = circleBuilder.getAnimation();

        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setAnimationEndedCallback(animationEnding -> {
            outerPolygon.show();
            circle.show();
        });
    }
}
