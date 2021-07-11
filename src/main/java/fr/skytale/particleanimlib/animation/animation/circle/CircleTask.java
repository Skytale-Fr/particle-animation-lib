package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircleTask extends AAnimationTask<Circle> {
    Vector currentU, currentV;

    public CircleTask(Circle circle) {
        super(circle);
        currentU = animation.getU().clone();
        currentV = animation.getV().clone();
        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        double stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        double radius = animation.getRadius().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);

        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * stepAngle;

            double x = iterationBaseLocation.getX() + (currentU.getX() * radius * Math.cos(theta)) + (currentV.getX() * radius * Math.sin(theta));
            double y = iterationBaseLocation.getY() + (currentU.getY() * radius * Math.cos(theta)) + (currentV.getY() * radius * Math.sin(theta));
            double z = iterationBaseLocation.getZ() + (currentU.getZ() * radius * Math.cos(theta)) + (currentV.getZ() * radius * Math.sin(theta));

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            showPoint(animation.getPointDefinition(), particleLocation, iterationBaseLocation);

        }

        if (animation.getRotationAxis() != null) {
            Vector rotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount);
            double rotationAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);

            currentU = new RotatableVector(currentU).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
            currentV = new RotatableVector(currentV).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
        }
    }
}
