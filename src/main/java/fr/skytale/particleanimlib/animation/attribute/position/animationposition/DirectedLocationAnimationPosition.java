package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DirectedLocationAnimationPosition extends AAnimationPosition {

    public DirectedLocationAnimationPosition(Location originLocation, Vector direction, Double speed) {
        this(originLocation, new Constant<>(direction), new Constant<>(speed));
    }

    public DirectedLocationAnimationPosition(Location originLocation, IVariable<Vector> direction, Double speed) {
        this(originLocation, direction, new Constant<>(speed));
    }

    public DirectedLocationAnimationPosition(Location originLocation, Vector direction, IVariable<Double> speed) {
        this(originLocation, new Constant<>(direction), speed);
    }

    public DirectedLocationAnimationPosition(Location originLocation, IVariable<Vector> direction, IVariable<Double> speed) {
        super(
                /* Creation of the origin directional vector
                   useful for animations like Helix that will use this vector to define the Hyperplane of the animation
                 */
                AnimationMove.createMove(
                        originLocation.clone().subtract(direction.getCurrentValue(0)),
                        direction.getCurrentValue(0)
                ),
                (iterationCount, previousMove) -> {
                    // Vector that represent a move in the given direction of the length corresponding to the given speed
                    final Vector currentDirection = direction.getCurrentValue(iterationCount);

                    Vector stepVector;
                    if (currentDirection == null || currentDirection.length() == 0) {
                        stepVector = new Vector(0, 0, 0);
                    } else {
                        stepVector = currentDirection.normalize().multiply(speed.getCurrentValue(iterationCount));
                    }

                    return AnimationMove.createMove(previousMove.getAfterMoveLocation(), stepVector);
                }
        );
    }

    protected DirectedLocationAnimationPosition(DirectedLocationAnimationPosition directedLocationAnimationPosition) {
        super(directedLocationAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new DirectedLocationAnimationPosition(this);
    }
}
