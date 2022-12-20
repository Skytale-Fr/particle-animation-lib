package fr.skytale.particleanimlib.animation.animation.rose;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RoseTask extends AAnimationTask<Rose> {

    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Double roseModifierNumerator;

    @IVariableCurrentValue
    private Integer roseModifierDenominator;

    @IVariableCurrentValue
    private Double radius;

    public RoseTask(Rose rose) {
        super(rose);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();
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
            double x = (U.getX() * radiusCosTheta) + (V.getX() * radiusSinTheta);
            double y = (U.getY() * radiusCosTheta) + (V.getY() * radiusSinTheta);
            double z = (U.getZ() * radiusCosTheta) + (V.getZ() * radiusSinTheta);

            animationPointsData.add(new AnimationPointData(new Vector(x, y, z)));
        }
        return animationPointsData;
    }
}
