package fr.skytale.particleanimlib.animation.animation.helix.preset;

import fr.skytale.particleanimlib.animation.animation.helix.HelixBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class DoubleHelixMovingUpDownPresetExecutor extends AAnimationPresetExecutor<HelixBuilder> {

    public DoubleHelixMovingUpDownPresetExecutor() {
        super(HelixBuilder.class);
    }

    @Override
    protected void apply(HelixBuilder helixBuilder, JavaPlugin plugin) {
        //iterationCount / 7d <=> speed : 0.1
        helixBuilder.setPosition(
                new DirectedLocationAnimationPosition( //Seulement DirectedLocationAnimationPosition accepté
                        helixBuilder.getOriginLocation(),
                        //new CallbackVariable<>(iterationCount -> new Vector(0, Math.cos(iterationCount / 14d), 0)),
                        new CallbackVariable<>(iterationCount -> {
                            double Y = Math.sin(iterationCount / 14d);
                            if (Y>=0)
                                return new Vector(0,1,0);
                            else
                                return new Vector(0,-1,0);
                        }),
                        0.05d)
        );
        helixBuilder.setHelixAngle(
                new CallbackVariable<>(
                        iterationCount -> {
                            if (Math.sin(iterationCount / 14d) > 0)
                                return Math.toRadians(20);
                            else
                                return -Math.toRadians(20);
                        }
                )
        );


        helixBuilder.setRadius(
                new CallbackVariable<>(iterationCount -> Math.sin(iterationCount / 14d) * 1.5)
        );

        helixBuilder.setNbHelix(2);
        helixBuilder.setPointDefinition(new ParticleTemplate(Particle.REDSTONE, Color.BLACK));

        helixBuilder.setTicksDuration(20 * 20);
    }
}
