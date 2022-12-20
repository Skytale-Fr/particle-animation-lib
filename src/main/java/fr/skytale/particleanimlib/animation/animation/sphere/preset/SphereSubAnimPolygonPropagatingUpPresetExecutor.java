package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SphereSubAnimPolygonPropagatingUpPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereSubAnimPolygonPropagatingUpPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(sphereBuilder.getPosition());
        polygonBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        polygonBuilder.setNbVertices(8);
        polygonBuilder.setDistanceBetweenPoints(0.4);
        polygonBuilder.setDistanceFromCenterToVertices(2);
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowPeriod(1);
        sphereBuilder.setRadius(10);
        sphereBuilder.setNbPoints(160);
        sphereBuilder.setPropagation(Sphere.PropagationType.BOTTOM_TO_TOP, 0.3f, 0.15f);
        sphereBuilder.setPointDefinition(new SubAnimPointDefinition(polygonBuilder.getAnimation()));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(3);
    }
}
