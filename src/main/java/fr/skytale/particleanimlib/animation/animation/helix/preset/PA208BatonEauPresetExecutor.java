package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.animation.sphere.Sphere;
import fr.skytale.particleanimlib.animation.animation.sphere.SphereBuilder;
import fr.skytale.particleanimlib.animation.animation.wave.WaveBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;

public class PA208BatonEauPresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public PA208BatonEauPresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        //Wave
        WaveBuilder waveBuilder = new WaveBuilder();
        waveBuilder.setJavaPlugin(plugin);
        waveBuilder.setPosition(helixBuilder.getPosition());
        waveBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.WATER_SPLASH, 0f));
        waveBuilder.setTicksDuration(400);
        waveBuilder.setShowPeriod(new Constant<>(0));
        waveBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        waveBuilder.setNbPoints(new Constant<>(100), true);
        waveBuilder.setRadiusMax(20);
        waveBuilder.setRadiusStart(Math.sin(20 * 2 / 28d) * 1.5);
        //Paillettes
        SphereBuilder sphereBuilder = new SphereBuilder();
        sphereBuilder.setJavaPlugin(plugin);
        sphereBuilder.setPosition(helixBuilder.getPosition());
        sphereBuilder.setSphereType(Sphere.Type.FULL);
        sphereBuilder.setTicksDuration(20*1);
        sphereBuilder.setShowPeriod(0);
        sphereBuilder.setRotation(new Vector(0,1,0), Math.PI/24);
        sphereBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.8f, new Vector(0.3,0.3,0.3), (ParticleData) null));
        sphereBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.exp(iterationCount-4)+4)
        );
        sphereBuilder.setNbPoints(10);

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
        helixBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, 1, 1, new Vector(0, 0, 0), new RegularColor(new Color(0, 0, 204))));
        helixBuilder.setTicksDuration(20 * 2);
        helixBuilder.setAnimationEndedCallback(animationEnding -> {
            waveBuilder.getAnimation().show();
            sphereBuilder.getAnimation().show();
        });
    }
}
