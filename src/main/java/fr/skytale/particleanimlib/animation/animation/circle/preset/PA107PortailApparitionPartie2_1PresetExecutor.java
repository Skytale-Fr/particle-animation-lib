package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class PA107PortailApparitionPartie2_1PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPartie2_1PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        double startRadius = 20d;
        double endRadius = 4d;

        circleBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(0, 0, 1),
                new Vector(1,0,0),
                Math.PI/84);//new DoublePeriodicallyEvolvingVariable(Math.PI/144,-Math.PI/12));

        circleBuilder.setNbPoints((int) (startRadius/2),true);
        circleBuilder.setRadius(endRadius);

        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.05f));
        circleBuilder.setTicksDuration(20*5);
    }
}
