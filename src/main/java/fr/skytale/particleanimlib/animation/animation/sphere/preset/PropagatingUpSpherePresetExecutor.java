package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;

import java.awt.*;

public class PropagatingUpSpherePresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PropagatingUpSpherePresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder) {
        sphereBuilder.setRadius(4);
        sphereBuilder.setNbCircles(20);
        sphereBuilder.setAngleBetweenEachPoint(Math.PI/4);
        sphereBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setPropagation(Sphere.PropagationType.BOTTOM_TO_TOP , 2);
        sphereBuilder.setTicksDuration(200);
        sphereBuilder.setShowFrequency(5);
    }
}
