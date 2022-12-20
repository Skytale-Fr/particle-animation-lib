package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Objects;

public class TargetingEntityAnimationPosition extends AAnimationPosition {

    public TargetingEntityAnimationPosition(Location originLocation, Entity targetedEntity, Double speed) {
        this(originLocation, new Constant<>(targetedEntity), new Constant<>(speed));
    }

    public TargetingEntityAnimationPosition(Location originLocation, IVariable<Entity> targetedEntity, Double speed) {
        this(originLocation, targetedEntity, new Constant<>(speed));
    }

    public TargetingEntityAnimationPosition(Location originLocation, Entity targetedEntity, IVariable<Double> speed) {
        this(originLocation, new Constant<>(targetedEntity), speed);
    }

    public TargetingEntityAnimationPosition(Location originLocation, IVariable<Entity> targetedEntity, IVariable<Double> speed) {
        super(
                /* Creation of the origin directional vector
                   useful for animations like Helix that will use this vector to define the Hyperplane of the animation
                 */
                AnimationMove.createMove(
                        originLocation,
                        targetedEntity.getCurrentValue(0).getLocation().toVector()
                                .subtract(originLocation.toVector())
                                .normalize()
                                .multiply(speed.getCurrentValue(0))
                ),
                (iterationCount, previousMove) -> {

                    final Entity currentTargetedEntity = targetedEntity.getCurrentValue(iterationCount);
                    // check if the entity can be targeted
                    if (currentTargetedEntity == null || !currentTargetedEntity.isValid() ||
                        currentTargetedEntity.isDead()) {
                        return AnimationMove.createError();
                    }

                    final Location moveOriginLocation = previousMove.getAfterMoveLocation();
                    final Location currentTargetedEntityLocation = currentTargetedEntity.getLocation();

                    // check if the targeted entity is still in the same world
                    if (!Objects.equals(moveOriginLocation.getWorld(), currentTargetedEntityLocation.getWorld())) {
                        return AnimationMove.createError();
                    }

                    // Vector that represent the move from the previousLocation to the entity
                    Vector fromLocationToEntityVector = currentTargetedEntityLocation.toVector().subtract(moveOriginLocation.toVector());

                    // Vector that represent a move in the direction of the entity of the length corresponding to the current speed
                    Vector stepVector = fromLocationToEntityVector.clone().normalize().multiply(speed.getCurrentValue(iterationCount));

                    // If the distance to the entity is too short, the new move will reach the entity
                    final boolean willReachTarget = stepVector.length() >= fromLocationToEntityVector.length();

                    return AnimationMove.createMove(
                            moveOriginLocation,
                            willReachTarget ? fromLocationToEntityVector : stepVector,
                            willReachTarget);
                }
        );
    }

    protected TargetingEntityAnimationPosition(TargetingEntityAnimationPosition targetingEntityAnimationPosition) {
        super(targetingEntityAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new TargetingEntityAnimationPosition(this);
    }

}
