package fr.skytale.particleanimlib.animation.animation.epi;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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
        List<Vector> animationPoints = new ArrayList<>();
        double epiModifier = epiModifierNumerator / epiModifierDenominator; //TODO CODE DUPLIQUE AVEC ROSE ET NODE
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
            double x = U.getX() * radiusCosTheta + V.getX() * radiusSinTheta;
            double y = U.getY() * radiusCosTheta + V.getY() * radiusSinTheta;
            double z = U.getZ() * radiusCosTheta + V.getZ() * radiusSinTheta;

            Vector particleLocation = new Vector(x, y, z);

            if (maxRadius == null || maxRadius >= particleLocation.length()) {
                animationPoints.add(particleLocation);
            }
        }

        return animationPoints;
    }
}
