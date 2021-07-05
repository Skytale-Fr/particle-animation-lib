package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attributes.var.parent.IVariable;
import org.bukkit.util.Vector;

public abstract class ARotatingAnimation extends AAnimation {

    private IVariable<Vector> rotationAxis;
    private IVariable<Double> rotationAngleAlpha;

    public IVariable<Vector> getRotationAxis() {
        return rotationAxis;
    }

    public void setRotationAxis(IVariable<Vector> rotationAxis) {
        this.rotationAxis = rotationAxis;
    }

    public IVariable<Double> getRotationAngleAlpha() {
        return rotationAngleAlpha;
    }

    public void setRotationAngleAlpha(IVariable<Double> rotationAngleAlpha) {
        this.rotationAngleAlpha = rotationAngleAlpha;
    }

    @Override
    public ARotatingAnimation clone() {
        ARotatingAnimation obj = null;
        obj = (ARotatingAnimation) super.clone();
        obj.rotationAxis = rotationAxis == null ? null : rotationAxis.copy();
        obj.rotationAngleAlpha = rotationAngleAlpha == null ? null : rotationAngleAlpha.copy();
        return obj;
    }
}
