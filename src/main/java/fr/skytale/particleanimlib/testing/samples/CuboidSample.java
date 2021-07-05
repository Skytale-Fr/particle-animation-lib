package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.*;
import fr.skytale.particleanimlib.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class CuboidSample implements IPAnimSample {

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(APosition position, JavaPlugin plugin) {
        CuboidBuilder cuboidBuilder = new CuboidBuilder();
        cuboidBuilder.setPosition(position);
        cuboidBuilder.setRotation(new VectorEquationEvolvingVariable("RANDOM()", "RANDOM()", "RANDOM()"), new DoubleEquationEvolvingVariable("PI*RANDOM()*0.3"));
        cuboidBuilder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-5, -5, -5), new Vector(0.1, 0.1, 0.1),10));
        cuboidBuilder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(5, 5, 5), new Vector(-0.1, -0.1, -0.1),10));
        cuboidBuilder.setDistanceBetweenPoints(new DoublePeriodicallyEvolvingVariable(0.3, -0.05, 10));

        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowFrequency(new Constant<>(4));
        cuboidBuilder.setJavaPlugin(plugin);

        return cuboidBuilder;
    }

    @Override
    public String getName() {
        return "cuboid";
    }
}
