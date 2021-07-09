package fr.skytale.particleanimlib.animation.animation.cuboid.preset;

import fr.skytale.particleanimlib.animation.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.util.Vector;

import java.awt.*;

public class CuboidPresetExecutor extends AAnimationPresetExecutor<CuboidBuilder> {

    public CuboidPresetExecutor() {
        super(CuboidBuilder.class);
    }

    @Override
    protected void apply(CuboidBuilder cuboidBuilder) {
        cuboidBuilder.setFromLocationToFirstCorner(new Vector(-4, -4, -4));
        cuboidBuilder.setFromLocationToSecondCorner(new Vector(4, 4, 4));
        cuboidBuilder.setDistanceBetweenPoints(new Constant<>(0.4));
        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowFrequency(new Constant<>(3));
    }
}
