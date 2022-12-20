package fr.skytale.particleanimlib.animation.attribute.position.animationposition;

import fr.skytale.particleanimlib.animation.attribute.position.attr.AnimationMove;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RelativeToEntityAnimationPosition extends AAnimationPosition {

    public RelativeToEntityAnimationPosition(Entity entity, Vector offsetVector) {
        this(new Constant<>(entity), new Constant<>(offsetVector));
    }

    public RelativeToEntityAnimationPosition(IVariable<Entity> entity, Vector offsetVector) {
        this(entity, new Constant<>(offsetVector));
    }

    public RelativeToEntityAnimationPosition(Entity entity, IVariable<Vector> offsetVector) {
        this(new Constant<>(entity), offsetVector);
    }

    public RelativeToEntityAnimationPosition(IVariable<Entity> entity, IVariable<Vector> offsetVector) {
        super(
                entity.getCurrentValue(0).getLocation(),
                (iterationCount, previousMove) -> AnimationMove.createMove(
                        previousMove.getAfterMoveLocation(),
                        entity.getCurrentValue(iterationCount).getLocation().clone()
                                .add(offsetVector.getCurrentValue(iterationCount))
                )
        );
    }

    protected RelativeToEntityAnimationPosition(RelativeToEntityAnimationPosition relativeToEntityAnimationPosition) {
        super(relativeToEntityAnimationPosition);
    }

    @Override
    public AAnimationPosition copy() {
        return new RelativeToEntityAnimationPosition(this);
    }
}
