package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.spiral.SpiralBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SphereSubAnimSpiralPropagatingUpPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereSubAnimSpiralPropagatingUpPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        SpiralBuilder spiralBuilder = new SpiralBuilder();
        spiralBuilder.setPosition(sphereBuilder.getPosition());
        spiralBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        spiralBuilder.applyPreset(AnimationPreset.SPIRAL, plugin);
        spiralBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(1.0, 0.2, 3));
        sphereBuilder.setRadius(5);
        sphereBuilder.setNbCircles(5);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI / 6);
        sphereBuilder.setPropagation(Sphere.PropagationType.BOTTOM_TO_TOP, 1);
        sphereBuilder.setPointDefinition(APointDefinition.fromSubAnim(spiralBuilder.getAnimation(), 1.0));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setTicksDuration(300);
        sphereBuilder.setShowPeriod(30);
    }
}
