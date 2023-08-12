package fr.skytale.particleanimlib.animation.animation.sphere.preset;

import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class PA206ProtectionArcanique4PresetExecutor extends AAnimationPresetExecutor<SphereBuilder> {

    public PA206ProtectionArcanique4PresetExecutor() {
        super(SphereBuilder.class);
    }

    @Override
    protected void apply(SphereBuilder sphereBuilder, JavaPlugin plugin) {
//        //TODO a supprimer, que pr le test
//        Location location = ((AAnimationPosition)(sphereBuilder.getPosition())).toIVariableLocation().getCurrentValue(0);
//        World monde = location.getWorld();
//        sphereBuilder.setPosition(new LocatedAnimationPosition(new Location(monde,-66.5,-52,-17.5)));

        //General params
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, Color.WHITE));
        sphereBuilder.setSphereType(Sphere.Type.HALF_TOP);
        sphereBuilder.setNbPoints(400);
        sphereBuilder.setRadius(8);

        //End of animation
        Sphere endSphere2 = new Sphere();
        for( int i=0; i<10 ; i++) {
            sphereBuilder.setPropagation(
                    Sphere.PropagationType.BOTTOM_TO_TOP,
                    0.1f,
                    0.2f);
            if (i>0) {
                Sphere finalEndSphere = endSphere2;
                sphereBuilder.setAnimationEndedCallback(animationEnding -> finalEndSphere.show());
            }

            Sphere endSphere1 = sphereBuilder.getAnimation();

            sphereBuilder.setPropagation(
                    Sphere.PropagationType.TOP_TO_BOTTOM,
                    0.1f,
                    0.2f);
            sphereBuilder.setAnimationEndedCallback(animationEnding -> endSphere1.show());
            endSphere2 = sphereBuilder.getAnimation();
        }

        //Start of animation
        double radiusChangeValue = 0.5;
        int tickDuration = (int) (8/radiusChangeValue);
        int nbPointChangeValue = 150 / tickDuration;
        sphereBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(0.001d, radiusChangeValue));
        sphereBuilder.setNbPoints(new IntegerPeriodicallyEvolvingVariable(0,nbPointChangeValue));
        sphereBuilder.setPropagation(null, 1f, 1f);
        sphereBuilder.setTicksDuration(tickDuration);
        Sphere finalEndSphere2 = endSphere2;
        sphereBuilder.setAnimationEndedCallback(animationEnding -> finalEndSphere2.show());
    }
}
