package fr.skytale.particleanimlib.animation.animation.mandala.preset;

import fr.skytale.particleanimlib.animation.animation.mandala.Mandala2DBuilder;
import fr.skytale.particleanimlib.animation.attribute.curve.CurvePointsGenerator;
import fr.skytale.particleanimlib.animation.attribute.range.LinearSpaceRange;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.plugin.java.JavaPlugin;

public class Mandala2DDiagLinesPresetExecutor extends AAnimationPresetExecutor<Mandala2DBuilder> {

    public Mandala2DDiagLinesPresetExecutor() {
        super(Mandala2DBuilder.class);
    }

    @Override
    protected void apply(Mandala2DBuilder mandala2DBuilder, JavaPlugin plugin) {
        mandala2DBuilder.setPoints(new CurvePointsGenerator<>(
                x -> new Vector2D(x, (x * -10) + 40),
                new LinearSpaceRange(0.5, 4, 30)
        ));
        mandala2DBuilder.setNbCircleSectionPairs(8);
    }
}
