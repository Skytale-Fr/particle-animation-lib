package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedRelativeAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class MovingPointCirclePresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    public MovingPointCirclePresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        AAnimationPosition position = (AAnimationPosition) circleBuilder.getPosition();
        circleBuilder.setPosition(
                new LocatedRelativeAnimationPosition(
                        position.toIVariableLocation(),
                        new CallbackVariable<>(iterationCount -> new Vector(0, Math.sin(iterationCount / 6) - 0.5, 0))
                )
        );
        circleBuilder.setNbPoints(1, true);
        circleBuilder.setRadius(1);
        circleBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 60);
        circleBuilder.setTicksDuration(20 * 10);
        circleBuilder.setPointDefinition(new ParticleTemplate(Particle.END_ROD, 0.01f));
    }
}