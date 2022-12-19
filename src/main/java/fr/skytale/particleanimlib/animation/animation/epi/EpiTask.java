package fr.skytale.particleanimlib.animation.animation.epi;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.List;

public class EpiTask extends AAnimationTask<Epi> {
    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Double epiModifierNumerator;

    @IVariableCurrentValue
    private Integer epiModifierDenominator;

    @IVariableCurrentValue
    private Double radius;

    @IVariableCurrentValue
    private Double maxRadius;


    public EpiTask(Epi epi) {
        super(epi);
        startTask();
    }

    @Override
    protected List<Vector> computeAnimationPoints() {
        return null;
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
        double epiModifierNumerator = animation.getEpiModifierNumerator().getCurrentValue(iterationCount);
        int epiModifierDenominator = animation.getEpiModifierDenominator().getCurrentValue(iterationCount);




        double epiModifier = epiModifierNumerator / epiModifierDenominator;
        double maxTheta;
        //defining maxTheta according to https://mathworld.wolfram.com/EpiCurve.html
        // If epiModifier is a rational number, then the curve closes at a computable polar angle.
        if ((epiModifier * 100000) % 1 == 0) { // if epiModifier is approximately a rational number
            if ((epiModifierDenominator * epiModifierNumerator) % 2 == 0) {
                // epiModifierNumerator * epiModifierDenominator is even
                maxTheta = Math.PI * epiModifierDenominator * 2;
            } else {
                // epiModifierNumerator * epiModifierDenominator is odd
                maxTheta = Math.PI * epiModifierDenominator;
            }
        } else {
            // If epirModifier is an irrational number, the curve never closes.
            maxTheta = 16 * Math.PI;
        }

        double stepTheta = maxTheta / nbPoints;
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            double distanceFromCenterToPoint = 1 / Math.cos(theta * epiModifier);

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
