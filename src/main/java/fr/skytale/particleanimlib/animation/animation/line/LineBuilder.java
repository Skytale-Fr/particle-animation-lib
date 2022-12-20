package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Objects;

public class LineBuilder extends AAnimationBuilder<Line, LineTask> {

    public static final String POINT1_SHOULD_NOT_BE_NULL = "point1 should not be null";
    public static final String POINT2_SHOULD_NOT_BE_NULL = "point2 should not be null";
    public static final String NB_POINTS_SHOULD_BE_POSITIVE = "nbPoints should be positive";
    public static final String DIRECTION_SHOULD_NOT_BE_NULL = "direction should not be null";
    public static final String LENGTH_SHOULD_BE_POSITIVE = "Length should be positive";

    public LineBuilder() {
        super();
        // Default values
        animation.setShowPeriod(new Constant<>(2));
        setNbPoints(10);
        setFromPositionToPoint2(new Vector(1, 0, 0), 3d);
        animation.setTicksDuration(60);
    }

    @Override
    protected Line initAnimation() {
        return new Line();
    }

    @Override
    public Line getAnimation() {
        checkNotNull(animation.getFromPositionToPoint1(), POINT1_SHOULD_NOT_BE_NULL);
        checkNotNull(animation.getFromPositionToPoint2(), POINT2_SHOULD_NOT_BE_NULL);
        checkPositiveAndNotNull(animation.getNbPoints(), NB_POINTS_SHOULD_BE_POSITIVE, false);
        return super.getAnimation();
    }

    /********* Line specific setters ***********/


    //******** Set points with 2 locations and set position on line center
    public void setAbsolutePointsAndCenterAnimPositionOnLineCenter(Location point1, Location point2) {
        setAbsolutePointsAndCenterAnimPositionOnLineCenter(new Constant<>(point1), new Constant<>(point2));
    }

    public void setAbsolutePointsAndCenterAnimPositionOnLineCenter(Location point1, IVariable<Location> point2) {
        setAbsolutePointsAndCenterAnimPositionOnLineCenter(new Constant<>(point1), point2);
    }

    public void setAbsolutePointsAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, Location point2) {
        setAbsolutePointsAndCenterAnimPositionOnLineCenter(point1, new Constant<>(point2));
    }

    public void setAbsolutePointsAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, IVariable<Location> point2) {
        checkNotNull(point1, POINT1_SHOULD_NOT_BE_NULL);
        checkNotNull(point2, POINT2_SHOULD_NOT_BE_NULL);

        final LocatedAnimationPosition position = new LocatedAnimationPosition(new CallbackVariable<>(iterationCount -> {
            Location point1Loc = point1.getCurrentValue(iterationCount);
            Location point2Loc = point2.getCurrentValue(iterationCount);
            return point1Loc.toVector().midpoint(point2Loc.toVector()).toLocation(Objects.requireNonNull(point1Loc.getWorld()));
        }));

        animation.setPosition(position);

        animation.setFromPositionToPoint1(new CallbackVariable<>(iterationCount ->
                point1.getCurrentValue(iterationCount).toVector()
                        .subtract(
                                position.getCurrentValue(iterationCount).getAfterMoveLocation().toVector()
                        )
        ));

        animation.setFromPositionToPoint1(new CallbackVariable<>(iterationCount ->
                point2.getCurrentValue(iterationCount).toVector()
                        .subtract(
                                position.getCurrentValue(iterationCount).getAfterMoveLocation().toVector()
                        )
        ));
    }

    //******** Set points with 2 locations and set position on point1
    public void setAbsolutePointsAndCenterAnimPositionOnPoint1(Location point1, Location point2) {
        setAbsolutePointsAndCenterAnimPositionOnPoint1(new Constant<>(point1), new Constant<>(point2));
    }

    public void setAbsolutePointsAndCenterAnimPositionOnPoint1(Location point1, IVariable<Location> point2) {
        setAbsolutePointsAndCenterAnimPositionOnPoint1(new Constant<>(point1), point2);
    }

    public void setAbsolutePointsAndCenterAnimPositionOnPoint1(IVariable<Location> point1, Location point2) {
        setAbsolutePointsAndCenterAnimPositionOnPoint1(point1, new Constant<>(point2));
    }

    public void setAbsolutePointsAndCenterAnimPositionOnPoint1(IVariable<Location> point1, IVariable<Location> point2) {
        checkNotNull(point1, POINT1_SHOULD_NOT_BE_NULL);
        checkNotNull(point2, POINT2_SHOULD_NOT_BE_NULL);

        final LocatedAnimationPosition position = new LocatedAnimationPosition(point1);
        animation.setPosition(position);

        animation.setFromPositionToPoint1(new CallbackVariable<>(iterationCount ->
                point1.getCurrentValue(iterationCount).toVector()
                        .subtract(
                                position.getCurrentValue(iterationCount).getAfterMoveLocation().toVector()
                        )
        ));

        animation.setFromPositionToPoint1(new CallbackVariable<>(iterationCount ->
                point2.getCurrentValue(iterationCount).toVector()
                        .subtract(
                                position.getCurrentValue(iterationCount).getAfterMoveLocation().toVector()
                        )
        ));
    }

    //******** Set points with a location and a vector and set position on line center
    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(Location point1, IVariable<Vector> direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(new Constant<>(point1), direction, length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, Vector direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(point1, new Constant<>(direction), length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, IVariable<Vector> direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(point1, direction, new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, Vector direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(point1, new Constant<>(direction), new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(Location point1, IVariable<Vector> direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(new Constant<>(point1), direction, new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(Location point1, Vector direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(new Constant<>(point1), new Constant<>(direction), length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnLineCenter(IVariable<Location> point1, IVariable<Vector> direction, IVariable<Double> length) {
        checkNotNull(point1, POINT1_SHOULD_NOT_BE_NULL);
        checkNotNull(direction, DIRECTION_SHOULD_NOT_BE_NULL);
        checkNotNull(length, LENGTH_SHOULD_BE_POSITIVE);

        IVariable<Location> point2 = new CallbackVariable<>(iterationCount ->
                point1.getCurrentValue(iterationCount).clone()
                        .add(
                                direction.getCurrentValue(iterationCount)
                                        .clone()
                                        .normalize()
                                        .multiply(
                                                length.getCurrentValue(iterationCount)
                                        ))
        );

        setAbsolutePointsAndCenterAnimPositionOnLineCenter(point1, point2);
    }

    //******** Set points with a location and a vector and set position on point1
    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(Location point1, IVariable<Vector> direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(new Constant<>(point1), direction, length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(IVariable<Location> point1, Vector direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(point1, new Constant<>(direction), length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(IVariable<Location> point1, IVariable<Vector> direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(point1, direction, new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(IVariable<Location> point1, Vector direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(point1, new Constant<>(direction), new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(Location point1, IVariable<Vector> direction, Double length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(new Constant<>(point1), direction, new Constant<>(length));
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(Location point1, Vector direction, IVariable<Double> length) {
        setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(new Constant<>(point1), new Constant<>(direction), length);
    }

    public void setAbsolutePointAndDirectionAndCenterAnimPositionOnPoint1(IVariable<Location> point1, IVariable<Vector> direction, IVariable<Double> length) {
        checkNotNull(point1, POINT1_SHOULD_NOT_BE_NULL);
        checkNotNull(direction, DIRECTION_SHOULD_NOT_BE_NULL);
        checkNotNull(length, LENGTH_SHOULD_BE_POSITIVE);

        IVariable<Location> point2 = new CallbackVariable<>(iterationCount ->
                point1.getCurrentValue(iterationCount).clone()
                        .add(
                                direction.getCurrentValue(iterationCount)
                                        .clone()
                                        .normalize()
                                        .multiply(
                                                length.getCurrentValue(iterationCount)
                                        ))
        );

        setAbsolutePointsAndCenterAnimPositionOnPoint1(point1, point2);

    }

    //******** Set point 1
    public void setFromPositionToPoint1(Vector fromPositionToPoint1) {
        setFromPositionToPoint1(new Constant<>(fromPositionToPoint1));
    }

    public void setFromPositionToPoint1(IVariable<Vector> fromPositionToPoint1) {
        animation.setFromPositionToPoint1(fromPositionToPoint1);
    }

    //******** Set point 1 on Position
    public void setPoint1OnPosition() {
        setFromPositionToPoint1(new Vector(0, 0, 0));
    }

    //******** Set point 1 by direction and length

    public void setFromPositionToPoint1(Vector direction, Double length) {
        setFromPositionToPoint1(direction, new Constant<>(length));
    }

    public void setFromPositionToPoint1(Vector direction, IVariable<Double> length) {
        setFromPositionToPoint1(new Constant<>(direction), length);
    }

    public void setFromPositionToPoint1(IVariable<Vector> direction, Double length) {
        setFromPositionToPoint1(direction, new Constant<>(length));
    }

    public void setFromPositionToPoint1(IVariable<Vector> direction, IVariable<Double> length) {
        setFromPositionToPoint1(new CallbackVariable<>(iterationCount ->
                direction.getCurrentValue(iterationCount)
                        .clone()
                        .normalize()
                        .multiply(
                                length.getCurrentValue(iterationCount)
                        )
        ));
    }

    //******** Set point 2
    public void setFromPositionToPoint2(Vector fromPositionToPoint2) {
        setFromPositionToPoint2(new Constant<>(fromPositionToPoint2));
    }

    public void setFromPositionToPoint2(IVariable<Vector> fromPositionToPoint2) {
        animation.setFromPositionToPoint2(fromPositionToPoint2);
    }

    //******** Set point 1 on Position
    public void setPoint2OnPosition() {
        setFromPositionToPoint2(new Vector(0, 0, 0));
    }

    //******** Set point 2 by direction and length

    public void setFromPositionToPoint2(Vector direction, Double length) {
        setFromPositionToPoint2(direction, new Constant<>(length));
    }

    public void setFromPositionToPoint2(Vector direction, IVariable<Double> length) {
        setFromPositionToPoint2(new Constant<>(direction), length);
    }

    public void setFromPositionToPoint2(IVariable<Vector> direction, Double length) {
        setFromPositionToPoint2(direction, new Constant<>(length));
    }

    public void setFromPositionToPoint2(IVariable<Vector> direction, IVariable<Double> length) {
        setFromPositionToPoint2(new CallbackVariable<>(iterationCount ->
                direction.getCurrentValue(iterationCount)
                        .clone()
                        .normalize()
                        .multiply(
                                length.getCurrentValue(iterationCount)
                        )
        ));
    }

    //******** Set nb points


    public void setNbPoints(IVariable<Integer> nbPoints) {
        animation.setNbPoints(nbPoints);
    }

    public void setNbPoints(int nbPoints) {
        animation.setNbPoints(new Constant<>(nbPoints));
    }
}
