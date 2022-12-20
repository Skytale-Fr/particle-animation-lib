package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocatedRelativeAnimationPosition extends AAnimationPosition {

    public LocatedRelativeAnimationPosition(IVariable<Location> location, Vector offsetVector) {
        this(location, new Constant<>(offsetVector));
    }

    public LocatedRelativeAnimationPosition(Location location, IVariable<Vector> offsetVector) {
        this(new Constant<>(location), offsetVector);
    }

    public LocatedRelativeAnimationPosition(Location location, Vector offsetVector) {
        this(new Constant<>(location), new Constant<>(offsetVector));

    }

    public LocatedRelativeAnimationPosition(IVariable<Location> location, IVariable<Vector> offsetVector) {
        super(
                location.getCurrentValue(0).clone()
                        .add(offsetVector.getCurrentValue(0)),
                (iterationCount, previousMove) -> AnimationMove.createMove(
                        previousMove.getAfterMoveLocation(),
                        location.getCurrentValue(iterationCount).clone()
                                .add(offsetVector.getCurrentValue(iterationCount))
                )
        );
    }

    protected LocatedRelativeAnimationPosition(LocatedRelativeAnimationPosition locatedRelativeAnimationPosition) {
        super(locatedRelativeAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new LocatedRelativeAnimationPosition(this);
    }
}
