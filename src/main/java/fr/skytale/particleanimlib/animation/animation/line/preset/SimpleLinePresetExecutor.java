package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class SimpleLinePresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public SimpleLinePresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {
        // Fetch the correct direction to set
        // from the current type of the position
        // (if the particle animation was set on a block
        // or is linked to an entity).
        APosition position = lineBuilder.getPosition();
        APosition.Type type = position.getType();
        IVariable<Vector> direction = null;
        switch (type) {
            case ENTITY: {
                // Follow the entity's looking direction
                Entity entity = position.getMovingEntity();
                direction = new CallbackVariable<>(iterationCount -> {
                    return entity.getLocation().getDirection();
                });
                break;
            }
            default: {
                direction = new Constant<>(new Vector(1, 0, 0));
                break;
            }
        }

//        lineBuilder.setDirection(direction);

        lineBuilder.setPoint1AtOrigin();
        lineBuilder.setDirection(direction);
        lineBuilder.setLength(new Constant<>(10.0d));
        lineBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));
//        lineBuilder.setLength(new Constant<>(10.0d));
    }
}
