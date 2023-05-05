package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA005FilOrPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public PA005FilOrPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {

        Vector directionValar = new Vector(-1, 0, 0); //TODO

        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(directionValar, new DoublePeriodicallyEvolvingVariable(0d, 0.1));
        lineBuilder.setTicksDuration(10 * 20);
        lineBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 1, 0.01f, new Vector(0.3,0.3,0.3), (ParticleData) null));
        lineBuilder.setNbPoints(new IntegerPeriodicallyEvolvingVariable(0, 1, 30));
        /*
        BLOCK_CRACK
            GOLD_BLOCK
        BLOCK_DUST
            GOLD_BLOCK
        END_ROD
         */
    }
}
