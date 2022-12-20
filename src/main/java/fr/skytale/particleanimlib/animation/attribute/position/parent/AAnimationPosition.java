package fr.skytale.particleanimlib.animation.attribute.position.parent;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackWithPreviousValueVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.ParametrizedCallbackWithPreviousValue;
import org.bukkit.Location;

public abstract class AAnimationPosition extends CallbackWithPreviousValueVariable<AnimationMove> implements IPosition {
    /**
     * Construct a callback variable
     *
     * @param callback the callback that will be able to return the current value
     */
    protected AAnimationPosition(Location location, ParametrizedCallbackWithPreviousValue<AnimationMove> callback) {
        super(AnimationMove.createOriginMove(location), callback);
    }

    /**
     * Construct a callback variable
     *
     * @param originMove the origin move (can already contain a move Vector for animations like Helix
     *                   that will use this vector to define the Hyperplane of the animation
     * @param callback   the callback that will be able to return the current value
     */
    protected AAnimationPosition(AnimationMove originMove, ParametrizedCallbackWithPreviousValue<AnimationMove> callback) {
        super(originMove, callback);
    }

    protected AAnimationPosition(AAnimationPosition aAnimationPosition) {
        super(aAnimationPosition);
    }


    /**
     * Clone a APosition
     *
     * @return the cloned AAnimationPosition
     */
    @Override
    public abstract AAnimationPosition copy();

    @Override
    public final PositionType getType() {
        return PositionType.NORMAL;
    }
}
