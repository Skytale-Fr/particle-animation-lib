package fr.skytale.particleanimlib.animation.animation.epi.preset;

import fr.skytale.particleanimlib.animation.animation.epi.EpiBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleEpiPresetExecutor extends AAnimationPresetExecutor<EpiBuilder> {

    public SimpleEpiPresetExecutor() {
        super(EpiBuilder.class);
    }

    @Override
    protected void apply(EpiBuilder epiBuilder, JavaPlugin plugin) {
        epiBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        epiBuilder.setNbPoints(200);
        epiBuilder.setRadius(3);
        epiBuilder.setMaxRadius(40);
        epiBuilder.setTicksDuration(3000);

        // Defining arbitrary sample values for N = numerator / denominator
        // source: https://mathcurve.com/courbes2d/rosace/rosace.shtml
        ArrayList<Double> epiModifiersNumerators = new ArrayList<>(Arrays.asList(
                2d, 3d, 4d, 5d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 4d, 5d, 7d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 3d, 4d, 6d, Math.sqrt(2), Math.E, Math.PI, 1d
        ));
        ArrayList<Integer> epiModifiersDenominators = new ArrayList<>(Arrays.asList(
                1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 1, 1, 1, 30
        ));
        epiBuilder.setEpiModifierNumerator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % epiModifiersNumerators.size();
            Double epiModifierNumerator = epiModifiersNumerators.get(index);
            Bukkit.broadcastMessage("numerator = " + epiModifierNumerator);
            return epiModifierNumerator;
        }));
        epiBuilder.setEpiModifierDenominator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % epiModifiersDenominators.size();
            int epiModifiersDenominator = epiModifiersDenominators.get(index);
            Bukkit.broadcastMessage("numerator = " + epiModifiersDenominator);
            return epiModifiersDenominator;
        }));

        epiBuilder.setShowPeriod(new Constant<>(1));
    }
}
