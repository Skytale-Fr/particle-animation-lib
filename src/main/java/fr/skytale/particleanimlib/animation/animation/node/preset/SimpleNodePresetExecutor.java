package fr.skytale.particleanimlib.animation.animation.node.preset;

import fr.skytale.particleanimlib.animation.animation.node.NodeBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleNodePresetExecutor extends AAnimationPresetExecutor<NodeBuilder> {

    public SimpleNodePresetExecutor() {
        super(NodeBuilder.class);
    }

    @Override
    protected void apply(NodeBuilder nodeBuilder, JavaPlugin plugin) {
        nodeBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        nodeBuilder.setNbPoints(200);
        nodeBuilder.setRadius(5);
        nodeBuilder.setMaxRadius(40);
        nodeBuilder.setTicksDuration(3000);

        // Defining arbitrary sample values for N = numerator / denominator
        // source: https://mathcurve.com/courbes2d/rosace/rosace.shtml
        ArrayList<Double> nodeModifiersNumerators = new ArrayList<>(Arrays.asList(
                2d, 3d, 4d, 5d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 4d, 5d, 7d, 1d, 3d, 5d, 7d, 9d, 1d, 2d, 3d, 4d, 6d, Math.sqrt(2), Math.E, Math.PI, 1d
        ));
        ArrayList<Integer> nodeModifiersDenominators = new ArrayList<>(Arrays.asList(
                1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 1, 1, 1, 30
        ));
        nodeBuilder.setNodeModifierNumerator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % nodeModifiersNumerators.size();
            Double nodeModifierNumerator = nodeModifiersNumerators.get(index);
            Bukkit.broadcastMessage("numerator = " + nodeModifierNumerator);
            return nodeModifierNumerator;
        }));
        nodeBuilder.setNodeModifierDenominator(new CallbackVariable<>(iterationCount -> {
            int index = Math.round(iterationCount / 60f) % nodeModifiersDenominators.size();
            int nodeModifiersDenominator = nodeModifiersDenominators.get(index);
            Bukkit.broadcastMessage("numerator = " + nodeModifiersDenominator);
            return nodeModifiersDenominator;
        }));

        nodeBuilder.setShowPeriod(new Constant<>(1));
    }
}
