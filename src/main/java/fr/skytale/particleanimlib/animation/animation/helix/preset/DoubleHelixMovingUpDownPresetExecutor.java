package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class DoubleHelixMovingUpDownPresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public DoubleHelixMovingUpDownPresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        helixBuilder.setPosition(
                new DirectedLocationAnimationPosition( //Seulement DirectedLocationAnimationPosition accepté
                        helixBuilder.getOriginLocation().add(0, 1, 0),
                        new CallbackVariable<>(iterationCount -> new Vector(0, Math.cos(iterationCount / 14d), 0)), //Quand expr == 0 erreur donc Math.sin(...) invalide ; si je fais 0.1 + Math.sin() ça monte continuellement
                        0.05d)
        );
        helixBuilder.setHelixAngle(
                new CallbackVariable<>(
                        iterationCount -> {
                            if (Math.cos(iterationCount / 14d) > 0)
                                return Math.toRadians(20);
                            else
                                return -Math.toRadians(20);
                        }
                )
        );


        helixBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.cos(iterationCount / 14d) * 1.5)
        );

        helixBuilder.setNbHelix(2);
        helixBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.REDSTONE, Color.BLACK));

        helixBuilder.setTicksDuration(20 * 20);
    }
}
