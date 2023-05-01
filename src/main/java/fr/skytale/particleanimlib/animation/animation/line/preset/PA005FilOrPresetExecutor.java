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

public class PA005FilOrPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public PA005FilOrPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {

        Vector directionValar = new Vector(-1, 0, 0); //TODO

        LineBuilder sparklyLineBuilder = new LineBuilder();
        sparklyLineBuilder.setPosition(lineBuilder.getPosition());
        sparklyLineBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        //Position = OnEntity = Rita
        sparklyLineBuilder.setPoint1OnPosition();
        sparklyLineBuilder.setFromPositionToPoint2(directionValar, new DoublePeriodicallyEvolvingVariable(0d, 0.1));
        sparklyLineBuilder.setTicksDuration(10 * 20);
        sparklyLineBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.01f));
        sparklyLineBuilder.setNbPoints(new IntegerPeriodicallyEvolvingVariable(0, 1, 4 * 2));

        LineBuilder goldenLineBuilder = new LineBuilder();
        goldenLineBuilder.setPosition(lineBuilder.getPosition());
        goldenLineBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        //Position = OnEntity = Rita
        goldenLineBuilder.setPoint1OnPosition();
        goldenLineBuilder.setFromPositionToPoint2(directionValar, new DoublePeriodicallyEvolvingVariable(0d, 0.1));
        goldenLineBuilder.setTicksDuration(10 * 20);
        goldenLineBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.BLOCK_DUST, Material.GOLD_BLOCK));
        goldenLineBuilder.setNbPoints(new IntegerPeriodicallyEvolvingVariable(0, 1, 4));

        lineBuilder.setPoint1OnPosition();
        lineBuilder.setTicksDuration(1);
        lineBuilder.setAnimationEndedCallback(animationEnding -> {
            sparklyLineBuilder.getAnimation().show();
//            goldenLineBuilder.getAnimation().show();
        });
        /*
        BLOCK_CRACK
            GOLD_BLOCK
        BLOCK_DUST
            GOLD_BLOCK
        END_ROD
         */
    }
}
