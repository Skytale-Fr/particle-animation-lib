package fr.skytale.particleanimlib.animation.animation.rose.preset;

import fr.skytale.particleanimlib.animation.animation.rose.RoseBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
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
        roseBuilder.setNbPoints(200, true);
        roseBuilder.setRadius(10);

        ArrayList<Double> roseModifiers = new ArrayList<>(Arrays.asList(2d, 3d, 4d, 5d, 1 / 2d, 3 / 2d, 5 / 2d, 7 / 2d, 9 / 2d, 1 / 3d, 2 / 3d, 4 / 3d, 5 / 3d, 7 / 3d, 1 / 4d, 3 / 4d, 5 / 4d, 7 / 4d, 9 / 4d, 1 / 5d, 2 / 5d, 3 / 5d, 4 / 5d, 6 / 5d));
        roseBuilder.setRoseModifier(new CallbackWithPreviousValueVariable<>(roseModifiers.get(0), (iterationCount, previousValue) -> {
            if (iterationCount % 60 != 0) {
                return previousValue;
            } else {
               int previousValueIndex = roseModifiers.indexOf(previousValue);
               int newIndex = previousValueIndex + 1;
               if (newIndex < roseModifiers.size()) {
                   return roseModifiers.get(newIndex);
               } else {
                   return roseModifiers.get(0);
               }
            }
        }));

        roseBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        roseBuilder.setTicksDuration(3000);
        roseBuilder.setShowPeriod(new Constant<>(1));
        roseBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 100);
    }
}
