package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.animation.polygon.PolygonBuilder;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.SubAnimPointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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
        polygonBuilder.setTicksDuration(1);
        polygonBuilder.setShowPeriod(1);

        // Fetch the correct direction to set
        // from the current type of the position
        // (if the particle animation was set on a block
        // or is linked to an entity).
        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(new Constant<>(new Vector(1, 0, 0)), new Constant<>(10d));
        lineBuilder.setPointDefinition(new SubAnimPointDefinition(polygonBuilder.getAnimation()));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(4));
        lineBuilder.setNbPoints(new Constant<>(10));
    }
}
