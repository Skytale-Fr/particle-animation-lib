package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA204AttaqueCorpsSol2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA204AttaqueCorpsSol2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.EXPLOSION_NORMAL, 1, 0f, new Vector(0, 0, 0)));
        circleBuilder.setNbPoints(5);
        circleBuilder.setRadius(new DoublePeriodicallyEvolvingVariable(0.5, 0.2));
        circleBuilder.setTicksDuration(5);

        circleBuilder.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        Circle circle1 = circleBuilder.getAnimation();

        circleBuilder.setRotation(new Vector(-1, 0, 0), new Vector(0, 0, -1));
        Circle circle2 = circleBuilder.getAnimation();

        circleBuilder.setTicksDuration(1);
        circleBuilder.setRadius(0.1);
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            circle1.show();
            circle2.show();
        });
    }
}
