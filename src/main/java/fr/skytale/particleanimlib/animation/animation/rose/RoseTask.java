package fr.skytale.particleanimlib.animation.animation.rose;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class RoseTask extends AAnimationTask<Rose> {
    Vector currentU, currentV;

    public RoseTask(Rose rose) {
        super(rose);
        currentU = animation.getU().clone();
        currentV = animation.getV().clone();
        startTask();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        double stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        double radius = animation.getRadius().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);
        double roseModifier = animation.getRoseModifier().getCurrentValue(iterationCount);

        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * stepAngle;

            double distanceFromCenterToPoint = Math.cos(theta*roseModifier);

            final double radiusCosTheta = radius * Math.cos(theta) * distanceFromCenterToPoint;
            final double radiusSinTheta = radius * Math.sin(theta) * distanceFromCenterToPoint;
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
}
