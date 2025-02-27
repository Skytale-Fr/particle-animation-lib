package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CuboidPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder, JavaPlugin plugin) {
        cuboidBuilder.setFromLocationToFirstCorner(new Vector(-4, -4, -4));
        cuboidBuilder.setFromLocationToSecondCorner(new Vector(4, 4, 4));
        cuboidBuilder.setDistanceBetweenPoints(0.4);
        cuboidBuilder.setTicksDuration(100);
        cuboidBuilder.setShowPeriod(3);
    }
}
