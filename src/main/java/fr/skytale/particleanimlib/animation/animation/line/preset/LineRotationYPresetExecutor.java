package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class LineRotationYPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public LineRotationYPresetExecutor() {
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
        Vector direction = null;
        switch (type) {
            case ENTITY: {
                Entity entity = position.getMovingEntity();
                direction = entity.getLocation().getDirection();
                break;
            }
            default: {
                direction = new Vector(1, 0, 0);
                break;
            }
        }

        lineBuilder.setDirection(direction);
        lineBuilder.setRotation(new Vector(0, 1, 0), new DoublePeriodicallyEvolvingVariable(0.0d, Math.PI / 10));
        lineBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));
        lineBuilder.setLength(new Constant<>(10.0d));
    }
}
