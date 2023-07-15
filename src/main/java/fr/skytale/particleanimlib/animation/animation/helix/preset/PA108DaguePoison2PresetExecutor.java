package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.animation.line.Line;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoid;
import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.CallbackPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.*;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.DustColorTransitionData;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;

public class PA108DaguePoison2PresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public PA108DaguePoison2PresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        //Exploding sparkles sphere
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

        //Exploding lines circle
        CircleBuilder explodingLinesCircleBuilder = new CircleBuilder();
        explodingLinesCircleBuilder.setJavaPlugin(plugin);
        explodingLinesCircleBuilder.setPosition(helixBuilder.getPosition());
        explodingLinesCircleBuilder.applyPreset(AnimationPreset.CIRCLE_EXPLODING_LINES, plugin);
        explodingLinesCircleBuilder.setRadius(2d);
        explodingLinesCircleBuilder.setTicksDuration(20*3 + 10);
        explodingLinesCircleBuilder.setShowPeriod(new IntegerPeriodicallyEvolvingVariable(10,-2,20));

        //Blinking sphere
        SphereBuilder poisonSphereBuilder = new SphereBuilder();
        poisonSphereBuilder.setJavaPlugin(plugin);
        poisonSphereBuilder.setPosition(helixBuilder.getPosition());
        poisonSphereBuilder.setSphereType(Sphere.Type.FULL);
        poisonSphereBuilder.setNbPoints(30);
        poisonSphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.DUST_COLOR_TRANSITION,new DustColorTransitionData(64, 190, 37, 255,255,255,1)));
        poisonSphereBuilder.setTicksDuration(20*3 + 10);
        poisonSphereBuilder.setShowPeriod(new IntegerPeriodicallyEvolvingVariable(10,-2,20));

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
        helixBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE,new Color(255, 255, 255)));
        helixBuilder.setNbHelix(2);
        helixBuilder.setTicksDuration(20 * 2);
        helixBuilder.setAnimationEndedCallback(animationEnding -> {
            //Update location of poision sphere
            Location latestLocation = animationEnding.getCurrentIterationBaseLocation();
            poisonSphereBuilder.setPosition(
                    new LocatedRelativeAnimationPosition(
                            latestLocation,
                            new Vector(0,0.5,0)
                    )
            );
            poisonSphereBuilder.setAnimationEndedCallback(task -> {
                sparklesSphereBuilder.getAnimation().show();
            });

            poisonSphereBuilder.getAnimation().show();


            explodingLinesCircleBuilder.setPosition(
                    new LocatedRelativeAnimationPosition(
                            latestLocation,
                            new Vector(0,0.5,0)
                    )
            );
            explodingLinesCircleBuilder.getAnimation().show();

        });
    }
}
