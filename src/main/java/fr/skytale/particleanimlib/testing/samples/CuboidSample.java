package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attributes.position.APosition;
import fr.skytale.particleanimlib.animation.attributes.var.Constant;
import fr.skytale.particleanimlib.animation.attributes.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attributes.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class CuboidSample implements IPAnimSample {

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(APosition position, JavaPlugin plugin) {
        CuboidBuilder cuboidBuilder = new CuboidBuilder();

        cuboidBuilder.setPosition(position);
        cuboidBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.toRadians(0), Math.toRadians(1), 0));
        cuboidBuilder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-3, -3, -3), new Vector(0.05, 0.1, 0.05), 10));
        cuboidBuilder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(3, 3, 3), new Vector(-0.05, -0.1, -0.05), 10));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowFrequency(new Constant<>(1));
        cuboidBuilder.setJavaPlugin(plugin);

        return cuboidBuilder;
    }

    @Override
    public String getName() {
        return "cuboid";
    }
}
