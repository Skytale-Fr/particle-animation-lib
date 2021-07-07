package fr.skytale.particleanimlib.animation.attribute.projectiledirection;

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

}
