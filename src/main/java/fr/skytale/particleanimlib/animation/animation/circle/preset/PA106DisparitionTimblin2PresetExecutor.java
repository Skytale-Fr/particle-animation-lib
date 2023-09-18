package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class PA106DisparitionTimblin2PresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA106DisparitionTimblin2PresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {

        circleBuilder.setNbPoints(5, true);
        circleBuilder.setTicksDuration(5);
        circleBuilder.setRotation(
                new Vector(1, 0, 0),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
                Math.PI / 6);


        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.EXPLOSION_NORMAL, 1, 0f, new Vector(0, 0, 0), (ParticleData) null));
        circleBuilder.setPosition(new LocatedAnimationPosition(circleBuilder.getOriginLocation()));
        circleBuilder.setRadius(new CallbackVariable<>(iterationCount -> Math.exp(iterationCount / 4d)));
        Circle bottomAnim = circleBuilder.getAnimation();


        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.EXPLOSION_NORMAL, 1, 0f, new Vector(0, 0, 0), (ParticleData) null));
        circleBuilder.setRadius(1);
        circleBuilder.setPosition(new LocatedRelativeAnimationPosition(
                circleBuilder.getOriginLocation(),
                new VectorPeriodicallyEvolvingVariable(new Vector(0, 0, 0), new Vector(0, 0.2, 0))
        ));
        Circle topAnim = circleBuilder.getAnimation();

        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.FLASH));
        circleBuilder.setRadius(0.1);
        circleBuilder.setNbPoints(1);
        circleBuilder.setTicksDuration(2);
        Circle flash = circleBuilder.getAnimation();

        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.EXPLOSION_NORMAL, 0f));
        circleBuilder.setAnimationEndedCallback(animationEnding -> {
            bottomAnim.show();
            topAnim.show();
            flash.show();
        });
    }
}
