package fr.skytale.particleanimlib.animation.animation.parabola.preset;

import fr.skytale.particleanimlib.animation.animation.parabola.ParabolaBuilder;
import fr.skytale.particleanimlib.animation.attribute.AnimationPreset;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class RandomizedRotatingParabolaPresetExecutor extends AAnimationPresetExecutor<ParabolaBuilder> {

    public RandomizedRotatingParabolaPresetExecutor() {
        super(ParabolaBuilder.class);
    }

    @Override
    protected void apply(ParabolaBuilder parabolaBuilder, JavaPlugin plugin) {
        // randomize direction callback with previous value
        // rotating circle preset
        AnimationPreset.PARABOLA.apply(parabolaBuilder, plugin);
        parabolaBuilder.setRotation(new Vector(0, 1, 0), Math.toRadians(5));
        parabolaBuilder.setDirection(AnimationDirection.fromMoveVector(new CallbackVariable<>(iterationCount -> {
            Vector u = new Vector(1, 0, 0);
            Vector v = new Vector(0, -1, 1);
            double radius = 1;
            double theta = ((1 + iterationCount) * 10) % (2 * Math.PI);

            double xDiff = (u.getX() * radius * Math.cos(theta)) + (v.getX() * radius * Math.sin(theta));
            double yDiff = (u.getY() * radius * Math.cos(theta)) + (v.getY() * radius * Math.sin(theta));
            double zDiff = (u.getZ() * radius * Math.cos(theta)) + (v.getZ() * radius * Math.sin(theta));

            return new Vector(xDiff, 1 + yDiff, 1 + zDiff);
        })));

        parabolaBuilder.setSpeed(new CallbackVariable<>(iterationCount -> Math.sin((2 + iterationCount) * 3)));
    }
}
