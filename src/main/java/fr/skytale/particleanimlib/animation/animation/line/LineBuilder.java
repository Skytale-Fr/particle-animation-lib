package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.Orientation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
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
        setPoint1AtOrigin();
        animation.setPoint2(new Constant<>(new Vector(10, 0, 0)));
//        animation.setDirection(new Vector(1, 0, 0));
        animation.setShowPeriod(new Constant<>(1));
        animation.setNbPoints(new Constant<>(10));
//        animation.setLength(new Constant<>(10.0d));
        animation.setTicksDuration(60);
    }

    @Override
    protected Line initAnimation() {
        return new Line();
    }

    /********* Line specific setters ***********/
    public void setPoint1(APosition position) {
        IVariable<Vector> point1 = getVectorVariableFromAPosition(position);
        animation.setPoint1(point1);
    }
    public void setPoint1AtOrigin() {
        IVariable<Vector> point1 = new Constant<>(new Vector(0, 0, 0));
        animation.setPoint1(point1);
    }
    public void setPoint2(APosition position) {
        IVariable<Vector> point2 = getVectorVariableFromAPosition(position);
        animation.setPoint1(point2);
    }
    public void setPoint2AtOrigin() {
        IVariable<Vector> point2 = new Constant<>(new Vector(0, 0, 0));
        animation.setPoint2(point2);
    }

    private IVariable<Vector> getVectorVariableFromAPosition(APosition position) {
        APosition.Type type = position.getType();
        IVariable<Vector> variable = null;
        switch (type) {
            case ENTITY: {
                Entity entity = position.getMovingEntity();
                variable = new CallbackVariable<>(iterationCount -> {
                    return entity.getLocation().toVector();
                });
                break;
            }
            case LOCATION: {
                variable = new CallbackVariable<>(iterationCount -> {
                    Location location = position.getLocation().getCurrentValue(iterationCount);
                    return location.toVector();
                });
                break;
            }
            case TRAIL: {
                variable = position.getRelativeLocation();
                break;
            }
            default: {
                throw new IllegalArgumentException(String.format("Position type '%s' is not handle yet.", type.name()));
            }
        }
        return variable;
    }

    public void setDirection(IVariable<Vector> direction) {
        checkNotNull(direction, DIRECTION_VECTOR_SHOULD_NOT_BE_NULL);

        AnimationDirection animationDirection = AnimationDirection.fromMoveVector(direction);

        IVariable<Vector> newPoint2 = new CallbackVariable<>(iterationCount -> {
            double lengthValue = animation.getLength().getCurrentValue(iterationCount);
            Vector directionVector = animationDirection.getMoveVector().getCurrentValue(iterationCount).clone();
            Vector toVector = directionVector.normalize().multiply(lengthValue);
            Vector newPosition2 = animation.getPoint1().getCurrentValue(iterationCount).clone().add(toVector);
            return newPosition2.clone();
        });

        animation.setPoint2(newPoint2);
    }

    public void setDirection(Vector direction) {
        setDirection(new Constant<>(direction));
    }

    public void setDirectionFromOrientation(Orientation orientation, double length) {
        Validate.isTrue(length > 0, LENGTH_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO);
        Vector direction = orientation.getDirection(length);
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
        IVariable<Vector> newPoint2 = new CallbackVariable<>(iterationCount -> {
            double lengthValue = length.getCurrentValue(iterationCount);
            Vector directionVector = animation.getDirection().getMoveVector().getCurrentValue(iterationCount).clone();
            Vector toVector = directionVector.normalize().multiply(lengthValue);
            Vector newPosition2 = animation.getPoint1().getCurrentValue(iterationCount).clone().add(toVector);
            return newPosition2.clone();
        });
        animation.setPoint2(newPoint2);
    }

    @Override
    public Line getAnimation() {
        checkNotNull(animation.getDirection(), DIRECTION_VECTOR_SHOULD_NOT_BE_NULL);
        checkPositiveAndNotNull(animation.getNbPoints(), NBPOINTS_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO, false);
        checkPositiveAndNotNull(animation.getLength(), LENGTH_SHOULD_NOT_BE_NULL_OR_LOWER_THAN_ZERO, true);
        return super.getAnimation();
    }
}
