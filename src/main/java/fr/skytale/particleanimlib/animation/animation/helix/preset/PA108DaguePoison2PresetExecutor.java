package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoid;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;

public class PA108DaguePoison2PresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public PA108DaguePoison2PresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        //Torus
        SphereBuilder posionSphereBuilder = new SphereBuilder();
        posionSphereBuilder.setJavaPlugin(plugin);
        posionSphereBuilder.setPosition(helixBuilder.getPosition());
        posionSphereBuilder.setSphereType(Sphere.Type.FULL);
//        posionSphereBuilder.setRotation(new Vector(0,1,0), Math.PI/24);
//        posionSphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.8f, new Vector(0.3,0.3,0.3), (ParticleData) null));
//        posionSphereBuilder.setRadius(
//                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount-4)+4)
//        );
        posionSphereBuilder.setNbPoints(30);
        posionSphereBuilder.setTicksDuration(20*3);
        posionSphereBuilder.setShowPeriod(10);

        //Paillettes
        SphereBuilder sparklesSphereBuilder = new SphereBuilder();
        sparklesSphereBuilder.setJavaPlugin(plugin);
        sparklesSphereBuilder.setPosition(helixBuilder.getPosition());
        sparklesSphereBuilder.setSphereType(Sphere.Type.FULL);
        sparklesSphereBuilder.setTicksDuration(20*1);
        sparklesSphereBuilder.setShowPeriod(0);
        sparklesSphereBuilder.setRotation(new Vector(0,1,0), Math.PI/24);
        sparklesSphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.8f, new Vector(0.3,0.3,0.3), (ParticleData) null));
        sparklesSphereBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount-4)+4)
        );
        sparklesSphereBuilder.setNbPoints(10);

        //Helix
        helixBuilder.setPosition(
                new DirectedLocationAnimationPosition( //Seulement DirectedLocationAnimationPosition accepté
                        helixBuilder.getOriginLocation(),
                        new Vector(0,1,0),
                        0.08d)
        );
        helixBuilder.setHelixAngle(Math.toRadians(25));
        helixBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.sin(iterationCount / 28d) * 1.5)
        );
        helixBuilder.setNbHelix(2);
        //helixBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, 1, 1, new Vector(0, 0, 0), new RegularColor(new Color(0, 0, 204))));
        helixBuilder.setTicksDuration(20 * 2);
        helixBuilder.setAnimationEndedCallback(animationEnding -> {
            Location latestLocation = animationEnding.getCurrentIterationBaseLocation();
            posionSphereBuilder.setPosition(new LocatedAnimationPosition(latestLocation));
            posionSphereBuilder.setAnimationEndedCallback(task -> sparklesSphereBuilder.getAnimation().show());
            posionSphereBuilder.getAnimation().show();
//            sparklesSphereBuilder.getAnimation().show();
        });
    }
}
