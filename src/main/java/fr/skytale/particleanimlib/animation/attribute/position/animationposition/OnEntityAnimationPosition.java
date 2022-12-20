package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.entity.Entity;

public class OnEntityAnimationPosition extends AAnimationPosition {

    public OnEntityAnimationPosition(Entity entity) {
        this(new Constant<>(entity));
    }

    public OnEntityAnimationPosition(IVariable<Entity> entity) {
        super(
                entity.getCurrentValue(0).getLocation(),
                (iterationCount, previousMove) -> AnimationMove.createMove(
                        previousMove.getAfterMoveLocation(),
                        entity.getCurrentValue(iterationCount).getLocation()
                )
        );
    }

    protected OnEntityAnimationPosition(OnEntityAnimationPosition onEntityAnimationPosition) {
        super(onEntityAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new OnEntityAnimationPosition(this);
    }
}
