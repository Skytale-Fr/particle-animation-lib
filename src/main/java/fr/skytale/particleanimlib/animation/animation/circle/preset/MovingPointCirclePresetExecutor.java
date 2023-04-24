package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.RelativeToEntityAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class MovingPointCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public MovingPointCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(1);
        circleBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 6);
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowPeriod(new Constant<>(1));
        circleBuilder.setPointDefinition(new ParticleTemplate(ParticleEffect.GLOW, 1, 0, new Vector(0, 0, 0), (ParticleData) null));

        //Step taken on Y axis
        double step = 0.05;
        //Nb ticks for the anim to reach the top of the player, given the step variable
        int nbTicks = (int) (2 / step);

        if (circleBuilder.getAnimation().getPosition() instanceof RelativeToEntityAnimationPosition) {
            System.out.println("Relative");

            //TODO changer LocatedAnimationPosition en RelativeToEntityAnimationPosition pour que Ã§a le suive
            circleBuilder.setPosition(
                    new LocatedAnimationPosition(
                            new CallbackWithPreviousValueVariable<>(
                                    circleBuilder.getOriginLocation(),
                                    (iterationCount, previousValue) -> {
                                        //Converting iterationCount in the [0 ; 2*nbTicks] interval
                                        int tick = iterationCount % (2 * nbTicks);

                                        if (tick >= 0 && tick < nbTicks) {
                                            return previousValue.add(0, step, 0);
                                        } else if (tick >= nbTicks && tick < 2 * nbTicks) {
                                            return previousValue.subtract(0, step, 0);
                                        } else {
                                            return previousValue;
                                        }
                                    })
                    )
            );

            Entity entity = null;
            circleBuilder.setPosition(
                    new RelativeToEntityAnimationPosition(
                            entity,
                            new CallbackWithPreviousValueVariable<>(
                                    new Vector(0, 0, 0),
                                    (iterationCount, previousValue) -> {
                                        //Converting iterationCount in the [0 ; 2*nbTicks] interval
                                        int tick = iterationCount % (2 * nbTicks);

                                        if (tick >= 0 && tick < nbTicks) {
                                            return previousValue.add(new Vector(0, step, 0));
                                        } else if (tick >= nbTicks && tick < 2 * nbTicks) {
                                            return previousValue.subtract(new Vector(0, step, 0));
                                        } else {
                                            return previousValue;
                                        }
                                    })
                    )
            );
        } else if (circleBuilder.getAnimation().getPosition() instanceof LocatedAnimationPosition) {
            System.out.println("Located");

            circleBuilder.setPosition(
                    new LocatedAnimationPosition(
                            new CallbackWithPreviousValueVariable<>(
                                    circleBuilder.getOriginLocation(),
                                    (iterationCount, previousValue) -> {
                                        //Converting iterationCount in the [0 ; 2*nbTicks] interval
                                        int tick = iterationCount % (2 * nbTicks);

                                        if (tick >= 0 && tick < nbTicks) {
                                            return previousValue.add(0, step, 0);
                                        } else if (tick >= nbTicks && tick < 2 * nbTicks) {
                                            return previousValue.subtract(0, step, 0);
                                        } else {
                                            return previousValue;
                                        }
                                    })
                    )
            );
        } else
            System.out.println(circleBuilder.getAnimation().getPosition().getClass());

    }
}