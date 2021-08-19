package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class LineWithPolygonsPresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public LineWithPolygonsPresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {
        // Describes a line with a polygon at every point
        // instead of a simple particle.

        // Polygon configuration

        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(lineBuilder.getPosition());
        polygonBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        polygonBuilder.setNbVertices(8);
        polygonBuilder.setDistanceBetweenPoints(0.4);
        polygonBuilder.setDistanceFromCenterToVertices(2);
        polygonBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowPeriod(1);

        // Fetch the correct direction to set
        // from the current type of the position
        // (if the particle animation was set on a block
        // or is linked to an entity).
        APosition position = lineBuilder.getPosition();
        APosition.Type type = position.getType();
        IVariable<Vector> direction = null;
        switch (type) {
            case ENTITY: {
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

        // Line configuration

        lineBuilder.setDirection(direction);
        lineBuilder.setPointDefinition(APointDefinition.fromSubAnim(polygonBuilder.getAnimation()));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(4));
        lineBuilder.setNbPoints(new Constant<>(10));
        lineBuilder.setLength(new Constant<>(20.0d));
    }
}
