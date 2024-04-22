package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.attribute.var.VectorPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PA203Attaque1ChargePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public PA203Attaque1ChargePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        AAnimationPosition position = (AAnimationPosition) circleBuilder.getPosition();

        //SubAnim 1
        CircleBuilder growingMovingCircleBuilder1 = new CircleBuilder();
        growingMovingCircleBuilder1.setJavaPlugin(plugin);
        growingMovingCircleBuilder1.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        growingMovingCircleBuilder1.setNbPoints(20, true);
        growingMovingCircleBuilder1.setTicksDuration(10);
        growingMovingCircleBuilder1.setPointDefinition(new ParticleTemplate(Particle.CRIT, 0f));
        growingMovingCircleBuilder1.setPosition(
                new LocatedRelativeAnimationPosition(
                        position.toIVariableLocation().copy(),
                        new VectorPeriodicallyEvolvingVariable(new Vector(0,-2,0), new Vector(0,0.4,0))
                )
        );
        growingMovingCircleBuilder1.setRadius(new DoublePeriodicallyEvolvingVariable(0.5, 0.05));
        Circle growingMovingCircle1 = growingMovingCircleBuilder1.getAnimation();

        //Sub Anim 2
        CircleBuilder growingMovingCircleBuilder2 = new CircleBuilder();
        growingMovingCircleBuilder2.setJavaPlugin(plugin);
        growingMovingCircleBuilder2.setRotation(new Vector(1, 0, 0), new Vector(0, 0, 1));
        growingMovingCircleBuilder2.setNbPoints(20, true);
        growingMovingCircleBuilder2.setTicksDuration(10);
        growingMovingCircleBuilder2.setPointDefinition(new ParticleTemplate(Particle.CRIT, 0f));
        growingMovingCircleBuilder2.setPosition(
                new LocatedRelativeAnimationPosition(
                        position.toIVariableLocation().copy(),
                        new VectorPeriodicallyEvolvingVariable(new Vector(0,-2,0), new Vector(0,0.4,0))
                )
        );
        growingMovingCircleBuilder2.setRadius(new DoublePeriodicallyEvolvingVariable(0.5, 0.05));
        growingMovingCircleBuilder2.setAnimationEndedCallback(animationEnding -> growingMovingCircle1.show());
        Circle growingMovingCircle2 = growingMovingCircleBuilder2.getAnimation();

        //Sub Anim 3
        CircleBuilder turningPointsCircleBuilder = new CircleBuilder();
        turningPointsCircleBuilder.setPosition(circleBuilder.getPosition());
        turningPointsCircleBuilder.setJavaPlugin(plugin);
        turningPointsCircleBuilder.setNbPoints(4);
        turningPointsCircleBuilder.setRotation(new Vector(0,1,0), Math.PI/3);
        turningPointsCircleBuilder.setTicksDuration(20 * 1);
        turningPointsCircleBuilder.setPointDefinition(new ParticleTemplate(Particle.CRIT, 0.01f));
        turningPointsCircleBuilder.setRadius(1.5);

        //Linking all anims
        circleBuilder.setRadius(0.1);
        circleBuilder.setTicksDuration(1);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.CRIT, 0f));
        circleBuilder.setAnimationEndedCallback(animationEnding ->
        {
            growingMovingCircle2.show();
            turningPointsCircleBuilder.getAnimation().show();
        });
    }
}
