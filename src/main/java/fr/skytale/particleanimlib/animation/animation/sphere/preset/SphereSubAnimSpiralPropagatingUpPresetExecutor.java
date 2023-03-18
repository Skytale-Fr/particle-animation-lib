package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SphereSubAnimSpiralPropagatingUpPresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public SphereSubAnimSpiralPropagatingUpPresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
        HelixBuilder helixBuilder = new HelixBuilder();
        helixBuilder.setPosition(sphereBuilder.getPosition());
        helixBuilder.setJavaPlugin(sphereBuilder.getJavaPlugin());
        helixBuilder.applyPreset(AnimationPreset.HELIX, plugin);
        helixBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(1.0, 0.2, 3));
        sphereBuilder.setRadius(5);
        sphereBuilder.setNbPoints(60);
        sphereBuilder.setPropagation(Sphere.PropagationType.BOTTOM_TO_TOP, 0.2f, 0.1f);
        sphereBuilder.setPointDefinition(new SubAnimPointDefinition(helixBuilder.getAnimation()));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setTicksDuration(300);
        sphereBuilder.setShowPeriod(30);
    }
}
