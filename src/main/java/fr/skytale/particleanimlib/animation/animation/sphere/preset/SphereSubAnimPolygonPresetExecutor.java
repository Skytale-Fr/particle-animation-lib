package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.Polygon;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.pyramid.PyramidBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.PointDefinition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class SphereSubAnimPolygonPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereSubAnimPolygonPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder) {
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(sphereBuilder.getPosition());
        polygonBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        polygonBuilder.setNbVertices(8);
        polygonBuilder.setDistanceBetweenPoints(0.4);
        polygonBuilder.setDistanceFromCenterToVertices(2);
        polygonBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowFrequency(1);
        sphereBuilder.setRadius(10);
        sphereBuilder.setNbCircles(7);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI / 4);
        sphereBuilder.setPointDefinition(PointDefinition.fromSubAnim(polygonBuilder.getAnimation()));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowFrequency(3);
    }
}
