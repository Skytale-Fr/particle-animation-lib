package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import org.bukkit.util.Vector;

public abstract class ARotatingAnimationBuilder<T extends ARotatingAnimation> extends AAnimationBuilder<T> {

    public void setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        animation.setRotationAxis(axis);
        animation.setRotationAngleAlpha(rotationAngleAlpha);
    }

    @Override
    public T getAnimation() {
        if (animation.getRotationAxis() != null) {
            checkNotNullOrZero(animation.getRotationAngleAlpha(), "RotationAngleAlpha should not be null or zero if RotationAxis is defined");
        }
        return super.getAnimation();
    }


}
