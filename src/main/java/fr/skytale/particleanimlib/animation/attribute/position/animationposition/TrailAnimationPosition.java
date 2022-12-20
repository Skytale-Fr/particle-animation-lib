package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.ParametrizedCallbackWithPreviousValue;

public class TrailAnimationPosition extends AAnimationPosition {
    public TrailAnimationPosition(AnimationMove originMove, ParametrizedCallbackWithPreviousValue<AnimationMove> callback) {
        super(originMove, callback);
    }

    protected TrailAnimationPosition(TrailAnimationPosition trailAnimationPosition) {
        super(trailAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new TrailAnimationPosition(this);
    }
}
