package fr.skytale.particleanimlib.animation.parent.task;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class ARotatingAnimationTask<T extends ARotatingAnimation> extends AAnimationTask<T> {

    protected PARotation rotation;
    protected final boolean hasRotation;
    protected final boolean hasChangingRotationAxis;
    protected final boolean hasChangingRotationAngle;
    protected Vector currentRotationAxis;
    protected Double currentRotationAngle;

    public ARotatingAnimationTask(T animation) {
        super(animation);
        this.hasRotation = animation.getRotationAxis() != null && animation.getRotationAngleAlpha() != null;
        this.hasChangingRotationAxis = hasRotation && !animation.getRotationAxis().isConstant();
        this.hasChangingRotationAngle = hasRotation && !animation.getRotationAngleAlpha().isConstant();
        if (hasRotation) {
            this.currentRotationAngle = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);
            this.currentRotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount).normalize();
            this.rotation = new PARotation(currentRotationAxis, currentRotationAngle);
        }
    }

    @Override
    public final void show(Location iterationBaseLocation) {
        // Stop if required
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        boolean changeRotation = hasRotation;

        //Modify rotationAxis if required
        if (hasChangingRotationAxis && animation.getRotationAxis().willChange(iterationCount)) {
            changeRotation = true;
            currentRotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount).normalize();
        }

        //Modify rotationStepAngleAlpha if required
        if (hasChangingRotationAngle && animation.getRotationAngleAlpha().willChange(iterationCount)) {
            changeRotation = true;
            currentRotationAngle = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);
            if (currentRotationAngle == 0) changeRotation = false;
        }

        if (changeRotation) {
            rotation.rotate(currentRotationAxis, currentRotationAngle);
        }

        //show the result (and ask for a rotation if required)
        showRotated(changeRotation, iterationBaseLocation);
    }

    protected abstract void showRotated(boolean rotationChanged, Location iterationBaseLocation);

}
