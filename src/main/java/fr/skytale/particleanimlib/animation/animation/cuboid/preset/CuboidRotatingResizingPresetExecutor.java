package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class CuboidRotatingResizingPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidRotatingResizingPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder) {
        cuboidBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.toRadians(0), Math.toRadians(1), 0));
        cuboidBuilder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-3, -3, -3), new Vector(0.05, 0.1, 0.05), 10));
        cuboidBuilder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(3, 3, 3), new Vector(-0.05, -0.1, -0.05), 10));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowFrequency(new Constant<>(1));
    }
}
