package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineTask extends ARotatingAnimationTask<Line> {

    public LineTask(Line line) {
        super(line);
        this.startTask();
    }

    @Override
    protected void showRotated(boolean rotationChanged, Location iterationBaseLocation) {
        if (this.hasDurationEnded()) {
            this.stopAnimation();
            return;
        }

        // If the animation has its final boundary binded
        // to a specific location, we need to update its direction
        if(animation.hasEndLocationBinded()) {
            animation.updateBindedEndLocation(iterationCount);
        }

        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);
        double length = animation.getLength().getCurrentValue(iterationCount);
        double step = (1.0D / nbPoints) * length; // Compute the step used in the drawLine method below

        // Get the current direction
        AnimationDirection direction = animation.getDirection();
        IVariable<Vector> moveVector = direction.getMoveVector();
        Vector currentValue = moveVector.getCurrentValue(iterationCount);
        Vector vDirection = currentValue.normalize();

        // If there is any rotation
        if(rotationChanged) {
            // Rotate around the provided axis
            // It's important here to clone the current vDirection vector.
            // Otherwise, the rotation angle could increase exponentially by
            // stacking over it-self.
            vDirection = vDirection.clone().rotateAroundAxis(currentRotationAxis, currentRotationAngle);
        }

        // Maybe this can be improved
        Vector lengthVector = vDirection.clone().multiply(length);
        Location startLocation = iterationBaseLocation.clone();
        Location endLocation = startLocation.clone().add(lengthVector);

        // Draw the current line from startLocation to endLocation
        drawLine(startLocation, endLocation, step, animation.getPointDefinition());
    }
}