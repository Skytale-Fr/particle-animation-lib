package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CuboidRotatingResizingPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidRotatingResizingPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder, JavaPlugin plugin) {
        cuboidBuilder.setRotation(new Constant<>(new Vector(0, 1, 0)), new DoublePeriodicallyEvolvingVariable(Math.toRadians(0), Math.toRadians(1), 0));
        cuboidBuilder.setFromLocationToFirstCorner(new VectorPeriodicallyEvolvingVariable(new Vector(-3, -3, -3), new Vector(0.05, 0.1, 0.05), 10));
        cuboidBuilder.setFromLocationToSecondCorner(new VectorPeriodicallyEvolvingVariable(new Vector(3, 3, 3), new Vector(-0.05, -0.1, -0.05), 10));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowPeriod(new Constant<>(1));
    }
}
