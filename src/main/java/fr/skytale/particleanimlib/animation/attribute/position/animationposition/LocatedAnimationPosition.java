package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;

public class LocatedAnimationPosition extends AAnimationPosition {

    public LocatedAnimationPosition(Location location) {
        this(new Constant<>(location));
    }

    public LocatedAnimationPosition(IVariable<Location> location) {
        super(
                location.getCurrentValue(0).clone(),
                (iterationCount, previousMove) -> AnimationMove.createMove(
                        previousMove.getAfterMoveLocation(),
                        location.getCurrentValue(iterationCount).clone()
                )
        );
    }

    protected LocatedAnimationPosition(LocatedAnimationPosition locatedAnimationPosition) {
        super(locatedAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new LocatedAnimationPosition(this);
    }
}
