package fr.skytale.particleanimlib.animation.animation.line.preset;

import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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

        lineBuilder.setPoint1OnPosition();
        lineBuilder.setFromPositionToPoint2(new Constant<>(new Vector(1, 0, 0)), new Constant<>(10d));
        lineBuilder.setRotation(new Vector(0, 1, 0), new Constant<>(Math.PI / 10));
        lineBuilder.setTicksDuration(100);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));
    }
}
