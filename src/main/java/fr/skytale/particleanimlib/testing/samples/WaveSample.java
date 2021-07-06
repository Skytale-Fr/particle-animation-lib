package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.wave.WaveBuilder;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Random;

public class WaveSample implements IPAnimSample {

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(APosition position, JavaPlugin plugin) {
        WaveBuilder waveBuilder = new WaveBuilder();

        waveBuilder.setPosition(position);
        waveBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        waveBuilder.setTicksDuration(400);
        waveBuilder.setShowFrequency(new Constant<>(0));
        waveBuilder.setJavaPlugin(plugin);
        waveBuilder.setDirectorVectors(new Vector(1,0,0), new Vector(0,0,1));
        waveBuilder.setNbPoints(new Constant<>(50), true);
        waveBuilder.setRadiusMax(20);
        waveBuilder.setRadiusStart(1);
        waveBuilder.setRadiusStep(new CallbackVariable<>(iterationCount -> 0.3 + Math.sin(iterationCount) / 4));

        return waveBuilder;
    }

    @Override
    public String getName() {
        return "wave";
    }
}
