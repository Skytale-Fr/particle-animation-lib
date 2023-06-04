package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class PA107PortailApparitionPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        double startRadius = 20d;
        double endRadius = 3d;
        int tickDuration = 20*5;

        circleBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(0, 0, 1),
                new Vector(1,0,0),
                Math.PI/48);
        /*circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.05f));
        circleBuilder.setDirectorVectors(new Vector(1, 0, 0), new Vector(0, 1, 0));
        circleBuilder.setNbPoints(10, true);
        double changeValue = (endRadius-startRadius)/tickDuration;*/
        circleBuilder.setRadius(2);//new DoublePeriodicallyEvolvingVariable(startRadius,  changeValue));
        circleBuilder.setTicksDuration(tickDuration);
    }
}
