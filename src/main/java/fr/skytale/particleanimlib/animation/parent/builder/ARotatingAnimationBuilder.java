package fr.skytale.particleanimlib.animation.parent.builder;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import org.bukkit.util.Vector;

public abstract class ARotatingAnimationBuilder<T extends ARotatingAnimation> extends AAnimationBuilder<T> {

    public void setRotation(IVariable<Vector> axis, IVariable<Double> rotationAngleAlpha) {
        animation.setRotationAxis(axis);
        animation.setRotationAngleAlpha(rotationAngleAlpha);
    }


    public void setRotation(Vector axis, IVariable<Double> rotationAngleAlpha) {
        setRotation(new Constant<>(axis), rotationAngleAlpha);
    }

    public void setRotation(IVariable<Vector> axis, double rotationAngleAlpha) {
        setRotation(axis, new Constant<>(rotationAngleAlpha));
    }

    public void setRotation(Vector axis, double rotationAngleAlpha) {
        setRotation(new Constant<>(axis), new Constant<>(rotationAngleAlpha));
    }

    @Override
    public T getAnimation() {
        if (animation.getRotationAxis() != null) {
            checkNotNullOrZero(animation.getRotationAngleAlpha(), "RotationAngleAlpha should not be null or zero if RotationAxis is defined");
        }
        return super.getAnimation();
    }


}
