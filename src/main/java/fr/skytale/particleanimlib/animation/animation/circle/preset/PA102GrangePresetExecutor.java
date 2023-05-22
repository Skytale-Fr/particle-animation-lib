package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

import java.awt.*;

public class PA102GrangePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA102GrangePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        //Explosition
        CircleBuilder explosionBuilder = new CircleBuilder();
        explosionBuilder.setJavaPlugin(plugin);
        explosionBuilder.setPosition(circleBuilder.getPosition());
        explosionBuilder.setNbPoints(5, true);
        explosionBuilder.setRadius(3);
        explosionBuilder.setTicksDuration(10);
        explosionBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.EXPLOSION_NORMAL, 2, 0.2f, new Vector(5, 5, 5), (ParticleData) null));

        //Dust
        CircleBuilder dustBuilder = new CircleBuilder();
        dustBuilder.setJavaPlugin(plugin);
        dustBuilder.setPosition(circleBuilder.getPosition());
        dustBuilder.setNbPoints(5, true);
        dustBuilder.setRadius(3);
        dustBuilder.setTicksDuration(10);
        dustBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.DUST_COLOR_TRANSITION, 2, 1f, new Vector(5, 5, 5), Color.WHITE));

        CircleBuilder ashBuilder = new CircleBuilder();
        ashBuilder.setJavaPlugin(plugin);
        ashBuilder.setPosition(circleBuilder.getPosition());
        ashBuilder.setNbPoints(5, true);
        ashBuilder.setRadius(3);
        ashBuilder.setTicksDuration(10);
        ashBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.ASH, 2, 1f, new Vector(5, 5, 5), (ParticleData) null));

        circleBuilder.setTicksDuration(1);
        circleBuilder.setRadius(1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.ASH));
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            explosionBuilder.getAnimation().show();
            dustBuilder.getAnimation().show();
            ashBuilder.getAnimation().show();
        });
    }
}