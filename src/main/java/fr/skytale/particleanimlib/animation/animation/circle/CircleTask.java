package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircleTask extends AAnimationTask<Circle> {
    Vector currentU, currentV;

    private double stepAngle;
    private double radius;
    private int nbPoints;

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

        stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        radius = animation.getRadius().getCurrentValue(iterationCount);
        nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);

        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * stepAngle;

            final double radiusCosTheta = radius * Math.cos(theta);
            final double radiusSinTheta = radius * Math.sin(theta);
            double x = iterationBaseLocation.getX() + (currentU.getX() * radiusCosTheta) + (currentV.getX() * radiusSinTheta);
            double y = iterationBaseLocation.getY() + (currentU.getY() * radiusCosTheta) + (currentV.getY() * radiusSinTheta);
            double z = iterationBaseLocation.getZ() + (currentU.getZ() * radiusCosTheta) + (currentV.getZ() * radiusSinTheta);

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

    public double getCurrentStepAngle() {
        return stepAngle;
    }

    public double getCurrentRadius() {
        return radius;
    }

    public int getCurrentNbPoints() {
        return nbPoints;
    }

}
