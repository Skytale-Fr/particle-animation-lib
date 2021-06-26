package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.cuboid.CuboidBuilder;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.BuildersInitializer;
import fr.skytale.particleanimlib.testing.attributes.AnimationType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class CuboidSample implements IParticleAnimSample {

    @Override
    public AAnimationBuilder<?> getInitializedBuilder(Player player, JavaPlugin plugin) {
        CuboidBuilder cuboidBuilder = new CuboidBuilder();
        cuboidBuilder.setAxis(new Vector(0, 1, 0));
        cuboidBuilder.setAxisChangeFrequency(0);
        cuboidBuilder.setStepAngleAlpha(Math.toRadians(3));
        cuboidBuilder.setStepAngleAlphaMax(Math.toRadians(30));
        cuboidBuilder.setStepAngleAlphaChangeFactor(2);
        cuboidBuilder.setStepAngleAlphaChangeFrequency(0);
        cuboidBuilder.setFromLocationToFirstCorner(new Vector(-3, -3, -3));
        cuboidBuilder.setFromLocationToSecondCorner(new Vector(3, 3, 3));
        cuboidBuilder.setMovingEntity(player);
        cuboidBuilder.setRelativeLocation(new Vector(0, 0, 0));
        cuboidBuilder.setStep(0.4);

        cuboidBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        cuboidBuilder.setTicksDuration(400);
        cuboidBuilder.setShowFrequency(5);
        cuboidBuilder.setJavaPlugin(plugin);

        return cuboidBuilder;
    }

    @Override
    public AnimationType getType() {
        return AnimationType.CUBOID;
    }
}
