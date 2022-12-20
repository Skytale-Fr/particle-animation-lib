package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Objects;

public class TargetingLocationAnimationPosition extends AAnimationPosition {

    public TargetingLocationAnimationPosition(Location originLocation, Location targetedLocation, Double speed) {
        this(originLocation, new Constant<>(targetedLocation), new Constant<>(speed));
    }

    public TargetingLocationAnimationPosition(Location originLocation, IVariable<Location> targetedLocation, Double speed) {
        this(originLocation, targetedLocation, new Constant<>(speed));
    }

    public TargetingLocationAnimationPosition(Location originLocation, Location targetedLocation, IVariable<Double> speed) {
        this(originLocation, new Constant<>(targetedLocation), speed);
    }

    public TargetingLocationAnimationPosition(Location originLocation, IVariable<Location> targetedLocation, IVariable<Double> speed) {
        super(
                /* Creation of the origin directional vector
                   useful for animations like Helix that will use this vector to define the Hyperplane of the animation
                 */
                AnimationMove.createMove(
                        originLocation,
                        targetedLocation.getCurrentValue(0).toVector()
                                .subtract(originLocation.toVector())
                                .normalize()
                                .multiply(speed.getCurrentValue(0))
                ),
                (iterationCount, previousMove) -> {

                    final Location currentTargetedLocation = targetedLocation.getCurrentValue(iterationCount);

                    final Location moveOriginLocation = previousMove.getAfterMoveLocation();

                    // check if the targeted entity is still in the same world
                    if (!Objects.equals(moveOriginLocation.getWorld(), currentTargetedLocation.getWorld())) {
                        return AnimationMove.createError();
                    }

                    // Vector that represent the move from the previousLocation to the entity
                    Vector fromLocationToLocationVector = currentTargetedLocation.toVector().subtract(moveOriginLocation.toVector());

                    // Vector that represent a move in the direction of the entity of the length corresponding to the current speed
                    Vector stepVector = fromLocationToLocationVector.clone().normalize().multiply(speed.getCurrentValue(iterationCount));

                    // If the distance to the entity is too short, the new move will reach the entity
                    final boolean willReachTarget = stepVector.length() >= fromLocationToLocationVector.length();

                    return AnimationMove.createMove(
                            moveOriginLocation,
                            willReachTarget ? fromLocationToLocationVector : stepVector,
                            willReachTarget);
                }
        );
    }

    protected TargetingLocationAnimationPosition(TargetingLocationAnimationPosition targetingLocationAnimationPosition) {
        super(targetingLocationAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new TargetingLocationAnimationPosition(this);
    }

}
