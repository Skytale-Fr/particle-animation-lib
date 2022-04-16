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

        double radius = animation.getRadius().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);
        double roseModifierNumerator = animation.getRoseModifierNumerator().getCurrentValue(iterationCount);
        int roseModifierDenominator = animation.getRoseModifierDenominator().getCurrentValue(iterationCount);
        double roseModifier = roseModifierNumerator / roseModifierDenominator;
        double maxTheta;
        //defining maxTheta according to https://mathworld.wolfram.com/RoseCurve.html
        // If roseModifier is a rational number, then the curve closes at a computable polar angle.
        if ((roseModifier * 100000) % 1 == 0) { // if roseModifier is approximately a rational number
            if ((roseModifierDenominator * roseModifierNumerator) % 2 == 0) {
                // roseModifierNumerator * roseModifierDenominator is even
                maxTheta = Math.PI * roseModifierDenominator * 2;
            } else {
                // roseModifierNumerator * roseModifierDenominator is odd
                maxTheta = Math.PI * roseModifierDenominator;
            }
        } else {
            // If roserModifier is an irrational number, the curve never closes.
            maxTheta = 16 * Math.PI;
        }

        double stepTheta = maxTheta / nbPoints;
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            double distanceFromCenterToPoint = Math.cos(theta * roseModifier);

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
