package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public abstract class ARotatingAnimationTask<T extends ARotatingAnimation> extends AAnimationTask<T> {

    protected Vector currentRotationAxis;
    protected double currentRotationStepAngleAlpha;

    public ARotatingAnimationTask(T animation) {
        super(animation);
        currentRotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount).normalize();
        currentRotationStepAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);
    }

    protected boolean hasRotation() {
        return animation.getRotationAxis() != null;
    }

    protected boolean hasChangingRotationAxis() {
        return hasRotation() && !animation.getRotationAxis().isConstant();
    }

    protected boolean hasChangingRotationStepAngle() {
        return hasRotation() && !animation.getRotationAngleAlpha().isConstant();
    }

    @Override
    public final void show(Location iterationBaseLocation) {
        // Stop if required
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        boolean changeRotation = hasRotation();

        //Modify rotationAxis if required
        if (hasChangingRotationAxis() && animation.getRotationAxis().willChange(iterationCount)) {
            changeRotation = true;
            currentRotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount).normalize();
        }

        //Modify rotationStepAngleAlpha if required
        if (hasChangingRotationStepAngle() && animation.getRotationAngleAlpha().willChange(iterationCount)) {
            changeRotation = true;
            currentRotationStepAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);
            if (currentRotationStepAngleAlpha == 0) changeRotation = false;
        }

        //show the result (and ask for a rotation if required)
        showRotated(changeRotation, iterationBaseLocation);
    }

    protected abstract void showRotated(boolean changeRotation, Location iterationBaseLocation);

}
