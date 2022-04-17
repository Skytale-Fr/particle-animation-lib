package fr.skytale.particleanimlib.animation.animation.nodes;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class NodeTask extends AAnimationTask<Node> {
    Vector currentU, currentV;

    public NodeTask(Node node) {
        super(node);
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
        Double maxRadius = animation.getMaxRadius() != null ? animation.getMaxRadius().getCurrentValue(iterationCount) : null;
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);
        double nodeModifierNumerator = animation.getNodeModifierNumerator().getCurrentValue(iterationCount);
        int nodeModifierDenominator = animation.getNodeModifierDenominator().getCurrentValue(iterationCount);
        double nodeModifier = nodeModifierNumerator / nodeModifierDenominator;
        double maxTheta;
        //defining maxTheta according to https://mathworld.wolfram.com/NodeCurve.html
        // If nodeModifier is a rational number, then the curve closes at a computable polar angle.
        if ((nodeModifier * 100000) % 1 == 0) { // if nodeModifier is approximately a rational number
            if ((nodeModifierDenominator * nodeModifierNumerator) % 2 == 0) {
                // nodeModifierNumerator * nodeModifierDenominator is even
                maxTheta = Math.PI * nodeModifierDenominator * 2;
            } else {
                // nodeModifierNumerator * nodeModifierDenominator is odd
                maxTheta = Math.PI * nodeModifierDenominator;
            }
        } else {
            // If noderModifier is an irrational number, the curve never closes.
            maxTheta = 16 * Math.PI;
        }

        double stepTheta = maxTheta / nbPoints;
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            double distanceFromCenterToPoint = 1 / Math.tan(theta * nodeModifier);

            final double radiusCosTheta = radius * Math.cos(theta) * distanceFromCenterToPoint;
            final double radiusSinTheta = radius * Math.sin(theta) * distanceFromCenterToPoint;
            double x = iterationBaseLocation.getX() + (currentU.getX() * radiusCosTheta) + (currentV.getX() * radiusSinTheta);
            double y = iterationBaseLocation.getY() + (currentU.getY() * radiusCosTheta) + (currentV.getY() * radiusSinTheta);
            double z = iterationBaseLocation.getZ() + (currentU.getZ() * radiusCosTheta) + (currentV.getZ() * radiusSinTheta);

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            if (maxRadius == null || maxRadius >= particleLocation.distance(iterationBaseLocation)) {
                showPoint(animation.getPointDefinition(), particleLocation, iterationBaseLocation);
            }
        }

        if (animation.getRotationAxis() != null) {
            Vector rotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount);
            double rotationAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);

            currentU = new RotatableVector(currentU).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
            currentV = new RotatableVector(currentV).rotateAroundAxis(rotationAxis, rotationAngleAlpha);
        }
    }
}
