package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.animation.circle.Circle;
import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingRoundAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineBuilder extends AAnimationBuilder<Line> {

    public static final String DIRECTION_VECTOR_SHOULD_NOT_BE_NULL = "direction vector should not be null";

    public LineBuilder() {
        super();
        // Default values
        animation.setDirection(new Vector(1, 0, 0));
        animation.setShowPeriod(new Constant<>(1));
        animation.setNbPoints(new Constant<>(10));
        animation.setLength(new Constant<>(10.0d));
        animation.setTicksDuration(60);
    }

    @Override
    protected Line initAnimation() {
        return new Line();
    }

    /********* Line specific setters ***********/
    public void setEndLocation(Location endLocation) {
        Location startLocation = animation.getPosition().getLocation().getCurrentValue(0);
        Vector direction = endLocation.clone().subtract(startLocation).toVector();
        animation.setDirection(direction);
    }

    public void setDirection(Vector direction) {
        checkNotNull(direction, DIRECTION_VECTOR_SHOULD_NOT_BE_NULL);
        animation.setDirection(direction);
    }

    public void setDirectionFromOrientation(Orientation direction, double length) {
        setDirection(direction.getU(length));
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        checkNotNull(pointDefinition, POINT_DEFINITION_SHOULD_NOT_BE_NULL);
        animation.setPointDefinition(pointDefinition);
    }

    public void setPointDefinition(ParticleTemplate particleTemplate) {
        setPointDefinition(APointDefinition.fromParticleTemplate(particleTemplate));
    }

    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
    }

    public void setLength(IVariable<Double> length) {
        animation.setLength(length);
    }

    @Override
    public Line getAnimation() {
        checkNotNull(animation.getDirection(), DIRECTION_VECTOR_SHOULD_NOT_BE_NULL);
        return super.getAnimation();
    }
}
