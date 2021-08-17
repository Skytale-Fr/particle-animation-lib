package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.IntegerPeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class LineWithCirclePresetExecutor extends AAnimationPresetExecutor<LineBuilder> {

    public LineWithCirclePresetExecutor() {
        super(LineBuilder.class);
    }

    @Override
    protected void apply(LineBuilder lineBuilder, JavaPlugin plugin) {
        PolygonBuilder polygonBuilder = new PolygonBuilder();
        polygonBuilder.setPosition(lineBuilder.getPosition());
        polygonBuilder.setJavaPlugin(lineBuilder.getJavaPlugin());
        polygonBuilder.setNbVertices(8);
        polygonBuilder.setDistanceBetweenPoints(0.4);
        polygonBuilder.setDistanceFromCenterToVertices(2);
        polygonBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowPeriod(1);

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
        // Also update the direction vectors of the polygon
        polygonBuilder.setDirectorVectorsFromNormalVector(direction);

        //lineBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(0, 255, 0), null));
        lineBuilder.setPointDefinition(APointDefinition.fromSubAnim(polygonBuilder.getAnimation()));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(4));
        lineBuilder.setNbPoints(new Constant<>(10));
        lineBuilder.setLength(new Constant<>(20.0d));
    }
}
