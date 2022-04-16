package fr.skytale.particleanimlib.animation.animation.rose.preset;

import fr.skytale.particleanimlib.animation.animation.rose.RoseBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleRosePresetExecutor extends AAnimationPresetExecutor<RoseBuilder> {

    public SimpleRosePresetExecutor() {
        super(RoseBuilder.class);
    }

    @Override
    protected void apply(RoseBuilder roseBuilder, JavaPlugin plugin) {
        roseBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        roseBuilder.setNbPoints(200);
        roseBuilder.setRadius(10);
        roseBuilder.setTicksDuration(3000);

        // Defining arbitrary sample values for N = numerator / denominator
        // source: https://mathcurve.com/courbes2d/rosace/rosace.shtml
        ArrayList<Double> roseModifiersNumerators = new ArrayList<>(Arrays.asList(
                2d, 3d, 4d, 5d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 4d, 5d, 7d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 3d, 4d, 6d, Math.sqrt(2), Math.E, Math.PI
        ));
        ArrayList<Integer> roseModifiersDenominators = new ArrayList<>(Arrays.asList(
                1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 1, 1, 1
        ));
        roseBuilder.setRoseModifierNumerator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % roseModifiersNumerators.size();
            Double roseModifierNumerator = roseModifiersNumerators.get(index);
            Bukkit.broadcastMessage("numerator = " + roseModifierNumerator);
            return roseModifierNumerator;
        }));
        roseBuilder.setRoseModifierDenominator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % roseModifiersDenominators.size();
            int roseModifiersDenominator = roseModifiersDenominators.get(index);
            Bukkit.broadcastMessage("numerator = " + roseModifiersDenominator);
            return roseModifiersDenominator;
        }));

        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        roseBuilder.setShowPeriod(new Constant<>(1));
    }
}
