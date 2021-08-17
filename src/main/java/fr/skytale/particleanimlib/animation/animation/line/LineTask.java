package fr.skytale.particleanimlib.animation.animation.line;

import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineTask extends AAnimationTask<Line> {
    Vector direction;

    public LineTask(Line line) {
        super(line);
        this.direction = ((Vector)line.getDirection().getMoveVector().getCurrentValue(0)).normalize();
        this.startTask();
    }

    public void show(Location iterationBaseLocation) {
        if (this.hasDurationEnded()) {
            this.stopAnimation();
        } else {
            int nbPoints = (Integer)((Line)this.animation).getNbPoints().getCurrentValue(this.iterationCount);
            double length = (Double)((Line)this.animation).getLength().getCurrentValue(this.iterationCount);
            double step = 1.0D / (double)nbPoints * length;
            Vector lengthVector = this.direction.clone().multiply(length);
            Location startLocation = iterationBaseLocation.clone();
            Location endLocation = startLocation.clone().add(lengthVector);
            this.drawLine(startLocation, endLocation, step, ((Line)this.animation).getPointDefinition());
        }
    }
}