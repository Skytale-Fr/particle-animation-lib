package fr.skytale.particleanimlib.animation.animation.torussolenoid;

import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class TorusSolenoidTask extends ARotatingAnimationTask<TorusSolenoid> {

    public TorusSolenoidTask(TorusSolenoid torusSolenoid) {
        super(torusSolenoid, torusSolenoid.getU(), torusSolenoid.getV());
        startTask();
    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void showRotated(boolean rotationChanged, Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        double torusRadius = animation.getTorusRadius().getCurrentValue(iterationCount);
        double solenoidRadius = animation.getSolenoidRadius().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);
        double torusModifierNumerator = animation.getTorusSolenoidModifierNumerator().getCurrentValue(iterationCount);
        int torusModifierDenominator = animation.getTorusSolenoidModifierDenominator().getCurrentValue(iterationCount);
        double torusModifier = torusModifierNumerator / torusModifierDenominator;
        double maxTheta;
        //defining maxTheta according to https://mathworld.wolfram.com/TorusSolenoidCurve.html
        // If torusModifier is a rational number, then the curve closes at a computable polar angle.
        if ((torusModifier * 100000) % 1 == 0) { // if torusModifier is approximately a rational number
            maxTheta = Math.PI * torusModifierDenominator * 2;
        } else {
            // If torusrModifier is an irrational number, the curve never closes.
            maxTheta = 16 * Math.PI;
        }

        double stepTheta = maxTheta / nbPoints;
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            final double cosTorusModifierTheta = Math.cos(torusModifier * theta);
            double x = (torusRadius + solenoidRadius * cosTorusModifierTheta) * Math.cos(theta);
            double y = (torusRadius + solenoidRadius * cosTorusModifierTheta) * Math.sin(theta);
            double z = solenoidRadius * Math.sin(torusModifier * theta);

            Vector fromBaseLocationToPoint = new Vector(x, y, z);

            Vector rotatedFromBaseLocationToPoint = rotation.rotateVector(fromBaseLocationToPoint);

            Location particleLocation = currentIterationBaseLocation.clone().add(rotatedFromBaseLocationToPoint);

            showPoint(animation.getPointDefinition(), particleLocation, iterationBaseLocation);

        }
    }
}
