package fr.skytale.particleanimlib.animation.animation.mandala.preset;

import fr.skytale.particleanimlib.animation.animation.mandala.Mandala2DBuilder;
import fr.skytale.particleanimlib.animation.attribute.curve.CurvePointsGenerator;
import fr.skytale.particleanimlib.animation.attribute.range.EvolvingRange;
import fr.skytale.particleanimlib.animation.attribute.range.LinearSpaceRange;
import fr.skytale.particleanimlib.animation.attribute.range.LogSpaceRange;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.plugin.java.JavaPlugin;

public class Mandala2DDiagLinesDavidStarLogSpaceEvolvingPresetExecutor extends AAnimationPresetExecutor<Mandala2DBuilder> {

    public Mandala2DDiagLinesDavidStarLogSpaceEvolvingPresetExecutor() {
        super(Mandala2DBuilder.class);
    }

    @Override
    protected void apply(Mandala2DBuilder mandala2DBuilder, JavaPlugin plugin) {
        mandala2DBuilder.setPoints(new CurvePointsGenerator<>(
                x -> new Vector2D(x, (x * -4) + 10),
                new EvolvingRange<>(
                        new LinearSpaceRange(0.5, 6, 123),
                        3
                )
        ));
        mandala2DBuilder.setNbCircleSectionPairs(8);
    }
}
