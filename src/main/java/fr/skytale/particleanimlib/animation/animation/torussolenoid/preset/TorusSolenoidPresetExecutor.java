package fr.skytale.particleanimlib.animation.animation.torussolenoid.preset;

import fr.skytale.particleanimlib.animation.animation.torussolenoid.TorusSolenoidBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class TorusSolenoidPresetExecutor extends AAnimationPresetExecutor<TorusSolenoidBuilder> {

    public TorusSolenoidPresetExecutor() {
        super(TorusSolenoidBuilder.class);
    }

    @Override
    protected void apply(TorusSolenoidBuilder torusSolenoidBuilder, JavaPlugin plugin) {
        torusSolenoidBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 0, 1));
        torusSolenoidBuilder.setNbPoints(200);
        ArrayList<Double> torusRadiuses = new ArrayList<>(Arrays.asList(
                6d, 2d, 0d
        ));
        torusSolenoidBuilder.setTorusRadius(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / (60f)) % torusRadiuses.size();
            Double torusRadius = torusRadiuses.get(index);
            Bukkit.broadcastMessage("torusRadius = " + torusRadius);
            return torusRadius;
        }));

        ArrayList<Double> solenoidRadiuses = new ArrayList<>(Arrays.asList(
                1.5d, 2d, 5d
        ));
        torusSolenoidBuilder.setSolenoidRadius(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / (60f)) % solenoidRadiuses.size();
            Double solenoidRadius = solenoidRadiuses.get(index);
            Bukkit.broadcastMessage("solenoidRadius = " + solenoidRadius);
            return solenoidRadius;
        }));
        torusSolenoidBuilder.setTicksDuration(4860);

        // Defining arbitrary sample values for N = numerator / denominator
        // source: https://mathcurve.com/courbes2d/rosace/rosace.shtml
        ArrayList<Double> torusSolenoidModifiersNumerators = new ArrayList<>(Arrays.asList(
                2d, 3d, 4d, 5d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 4d, 5d, 7d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 3d, 4d, 6d, Math.sqrt(2), Math.E, Math.PI
        ));
        ArrayList<Integer> torusSolenoidModifiersDenominators = new ArrayList<>(Arrays.asList(
                1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 1, 1, 1
        ));
        torusSolenoidBuilder.setTorusSolenoidModifierNumerator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / (3 * 60f)) % torusSolenoidModifiersNumerators.size();
            Double torusSolenoidModifierNumerator = torusSolenoidModifiersNumerators.get(index);
            Bukkit.broadcastMessage("numerator = " + torusSolenoidModifierNumerator);
            return torusSolenoidModifierNumerator;
        }));
        torusSolenoidBuilder.setTorusSolenoidModifierDenominator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / (3 * 60f)) % torusSolenoidModifiersDenominators.size();
            int torusSolenoidModifiersDenominator = torusSolenoidModifiersDenominators.get(index);
            Bukkit.broadcastMessage("numerator = " + torusSolenoidModifiersDenominator);
            return torusSolenoidModifiersDenominator;
        }));

        torusSolenoidBuilder.setShowPeriod(new Constant<>(1));
    }
}
