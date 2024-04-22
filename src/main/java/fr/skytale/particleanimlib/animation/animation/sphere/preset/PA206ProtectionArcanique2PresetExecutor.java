package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;


public class PA206ProtectionArcanique2PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA206ProtectionArcanique2PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(sphereBuilder.getPosition());
        polygonBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        polygonBuilder.setNbVertices(5);
        polygonBuilder.setDistanceBetweenPoints(0.4);
        polygonBuilder.setDistanceFromCenterToVertices(1); //HERE
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowPeriod(1);
        polygonBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.WHITE)); //HERE

        sphereBuilder.setRadius(8);
        sphereBuilder.setNbPoints(90); //HERE
        sphereBuilder.setPointDefinition(new SubAnimPointDefinition(polygonBuilder.getAnimation(), new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION, 0d, SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION)));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(3);
    }
}
