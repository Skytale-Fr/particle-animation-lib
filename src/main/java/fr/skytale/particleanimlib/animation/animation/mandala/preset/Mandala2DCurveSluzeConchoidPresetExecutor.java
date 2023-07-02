package fr.skytale.particleanimlib.animation.animation.mandala.preset;

import fr.skytale.particleanimlib.animation.animation.mandala.Mandala2DBuilder;
import fr.skytale.particleanimlib.animation.attribute.curve.CurvePointsGenerator;
import fr.skytale.particleanimlib.animation.attribute.range.LinearSpaceRange;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.plugin.java.JavaPlugin;

public class Mandala2DCurveSluzeConchoidPresetExecutor extends AAnimationPresetExecutor<Mandala2DBuilder> {

    public Mandala2DCurveSluzeConchoidPresetExecutor() {
        super(Mandala2DBuilder.class);
    }

    @Override
    protected void apply(Mandala2DBuilder mandala2DBuilder, JavaPlugin plugin) {
        // Curve from https://en.wikipedia.org/wiki/Conchoid_of_de_Sluze
        final double alpha = -2.0; // alpha = −2 result in the "right strophoid" (https://en.wikipedia.org/wiki/Strophoid)
        final int N = 50; // point count
        mandala2DBuilder.setPoints(new CurvePointsGenerator<>(
                theta -> {
                    double r = (1.0 / Math.cos(theta)) + alpha * Math.cos(theta) * 3;
                    return new Vector2D(r * Math.cos(theta), r * Math.sin(theta));
                },
                new LinearSpaceRange(0.0, Math.PI * 2, N)
        ));
        mandala2DBuilder.setNbCircleSection(4);
    }
}
