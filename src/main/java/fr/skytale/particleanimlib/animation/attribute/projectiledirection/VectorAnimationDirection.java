package fr.skytale.particleanimlib.animation.attribute.projectiledirection;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.util.Vector;

public class VectorAnimationDirection extends AnimationDirection {

    public VectorAnimationDirection(IVariable<Vector> moveVector) {
        super(
                moveVector,
                null,
                null,
                null,
                Type.MOVE_VECTOR
        );
    }

    public VectorAnimationDirection(Vector vector) {
        this(new Constant<>(vector));
    }

    @Override
    public VectorAnimationDirection clone() {
        return (VectorAnimationDirection) super.clone();
    }

}
