package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class LineBuilder extends ARotatingAnimationBuilder<Line> {

    public static final String DIRECTION_VECTOR_SHOULD_NOT_BE_NULL = "direction vector should not be null";
    public static final String ENDLOCATION_SHOULD_NOT_BE_NULL = "endLocation should not be null";
    public static final String POSITION_SHOULD_BE_SET = "Animation position should be set";
    public static final String NBPOINTS_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO = "nbPoints should not be null or lower than zero";
    public static final String LENGTH_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO = "length should not be null or lower than zero";

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
        checkNotNull(endLocation, ENDLOCATION_SHOULD_NOT_BE_NULL);

        APosition position = animation.getPosition();
        checkNotNull(position, POSITION_SHOULD_BE_SET);

        // Compute the direction vector
        Location startLocation = getLocationFromAPosition(position);
        Vector toVector = endLocation.clone().subtract(startLocation).toVector();
        // Get it's length and update the animation's length
        double length = toVector.length();
        setLength(new Constant<>(length));

        // Normalize and set the direction
        Vector direction = toVector.normalize();
        animation.setDirection(direction);
    }

    public void setEndLocation(APosition position) {
        Location endLocation = getLocationFromAPosition(position);
        setEndLocation(endLocation);
    }

    /**
     * The difference between setEndLocation and bindEndLocation can be found
     * in time. With bindEndLocation, the boundary of the line will always be linked to
     * the specified location. Even if the animation's position or the end location is changing.
     * @param endLocation
     */
    public void bindEndLocation(IVariable<Location> endLocation) {
        animation.bindEndLocation(endLocation);
    }
    public void bindEndLocation(APosition position) {
        IVariable<Location> endLocation = null;
        APosition.Type type = position.getType();
        switch (type) {
            case ENTITY: {
                Entity entity = position.getMovingEntity();
                // Use a callback variable to fetch the entity's location every getCurrentValue calls.
                endLocation = new CallbackVariable<>(iterationCount -> entity.getLocation());
                break;
            }
            default: {
                Location location = position.getLocation().getCurrentValue(0);
                endLocation = new Constant<>(location);
                break;
            }
        }
        bindEndLocation(endLocation);
    }

    /**
     * Fetch the location from a APosition based
     * from it's inner type (ENTITY, LOCATION, TRAIL)
     * @param position
     * @return the fetched location
     */
    private Location getLocationFromAPosition(APosition position) {
        APosition.Type type = position.getType();
        Location location = null;
        switch (type) {
            case ENTITY: {
                Entity entity = position.getMovingEntity();
                location = entity.getLocation();
                break;
            }
            default: {
                location = position.getLocation().getCurrentValue(0);
                break;
            }
        }
        return location;
    }

    public void setDirection(Vector direction) {
        checkNotNull(direction, DIRECTION_VECTOR_SHOULD_NOT_BE_NULL);
        animation.setDirection(direction);
    }

    public void setDirectionFromOrientation(Orientation orientation, double length) {
        Validate.isTrue(length > 0, LENGTH_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO);
        Vector u = orientation.getU(length);
        Vector v = orientation.getV(length);
        Vector direction = u.crossProduct(v);
        setDirection(direction);
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
        checkPositiveAndNotNull(animation.getNbPoints(), NBPOINTS_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO, false);
        checkPositiveAndNotNull(animation.getLength(), LENGTH_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO, true);
        return super.getAnimation();
    }
}
