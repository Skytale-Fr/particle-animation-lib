package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class SphereSubAnimRotatingCirclesPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereSubAnimRotatingCirclesPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(2);
        circleBuilder.setRotation(new Vector(0,1,0), Math.PI/120);
        circleBuilder.setShowPeriod(new Constant<>(1));
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.ELECTRIC_SPARK, 1, 0, new Vector(0,0,0), (ParticleData) null));
        circleBuilder.setPosition(sphereBuilder.getPosition());
        circleBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());

        sphereBuilder.setRadius(3);
        sphereBuilder.setNbPoints(15);
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(100);
        sphereBuilder.setShowPeriod(20);
        sphereBuilder.setPointDefinition(new SubAnimPointDefinition(
                circleBuilder.getAnimation(),
                new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION)
        ));
    }
}