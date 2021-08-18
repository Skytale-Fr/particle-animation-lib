package fr.skytale.particleanimlib.animation.animation.circle.preset;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.animation.line.LineBuilder;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.DoublePeriodicallyEvolvingVariable;
import fr.skytale.particleanimlib.animation.parent.preset.AAnimationPresetExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.awt.*;

public class HelicopterPresetExecutor extends AAnimationPresetExecutor<CircleBuilder> {

    private static final int PROPELLER_COUNT = 3;
    private static final Orientation ORIENTATION = Orientation.UP; // The orientation of the circle handling all lines

    public HelicopterPresetExecutor() {
        super(CircleBuilder.class);
    }

    @Override
    protected void apply(CircleBuilder circleBuilder, JavaPlugin plugin) {
        LineBuilder lineBuilder = new LineBuilder();
        lineBuilder.setPosition(circleBuilder.getPosition());
        lineBuilder.setJavaPlugin(circleBuilder.getJavaPlugin());

        lineBuilder.setMainParticle(new ParticleTemplate("REDSTONE", new Color(255, 170, 0), null));
        APosition position = circleBuilder.getPosition();
        lineBuilder.bindEndLocation(position);
        lineBuilder.setTicksDuration(1);
        lineBuilder.setShowPeriod(new Constant<>(1));
        lineBuilder.setNbPoints(new Constant<>(50));

        circleBuilder.setDirectorVectorsFromOrientation(ORIENTATION, 1);
        circleBuilder.setNbPoints(PROPELLER_COUNT, true);
        circleBuilder.setRadius(10);
        circleBuilder.setRotation(new Vector(0, 1, 0), Math.PI / 10);
        circleBuilder.setPointDefinition(APointDefinition.fromSubAnim(lineBuilder.getAnimation()));
        circleBuilder.setTicksDuration(100);
        circleBuilder.setShowPeriod(new Constant<>(3));
    }
}
