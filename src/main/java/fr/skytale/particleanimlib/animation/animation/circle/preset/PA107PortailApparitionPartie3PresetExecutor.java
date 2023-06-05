package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

public class PA107PortailApparitionPartie3PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA107PortailApparitionPartie3PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        circleBuilder.setNbPoints(5,true);
        circleBuilder.setRadius(new CallbackVariable<>(iterationCount -> Math.sin(iterationCount/6)/4 + 4));

        circleBuilder.setRotation(
                new Vector(0, 1, 0),
                new Vector(0, 0, 1),
                new Vector(1,0,0),
                Math.PI/12);

        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.END_ROD, 0.05f));
        circleBuilder.setTicksDuration(20*10);
    }
}
