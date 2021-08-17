package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineTask extends AAnimationTask<Line> {

    public LineTask(Line line) {
        super(line);
        this.startTask();
    }

    public void show(Location iterationBaseLocation) {
        if (this.hasDurationEnded()) {
            this.stopAnimation();
            return;
        }

        int nbPoints = this.animation.getNbPoints().getCurrentValue(iterationCount);
        double length = this.animation.getLength().getCurrentValue(iterationCount);
        double step = (1.0D / nbPoints) * length; // Compute the step used in the drawLine method below

        // Get the current direction
        AnimationDirection direction = animation.getDirection();
        IVariable<Vector> moveVector = direction.getMoveVector();
        Vector currentValue = moveVector.getCurrentValue(iterationCount);
        Vector vDirection = currentValue.normalize();

        // Maybe this can be improved
        Vector lengthVector = vDirection.clone().multiply(length);
        Location startLocation = iterationBaseLocation.clone();
        Location endLocation = startLocation.clone().add(lengthVector);

        // Draw the current line from startLocation to endLocation
        drawLine(startLocation, endLocation, step, animation.getPointDefinition());

    }
}