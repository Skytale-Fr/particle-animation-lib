package fr.skytale.particleanimlib.animation.animation.nodes;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class NodeTask extends AAnimationTask<Node> {
    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Double nodeModifierNumerator;

    @IVariableCurrentValue
    private Integer nodeModifierDenominator;

    @IVariableCurrentValue
    private Double radius;

    @IVariableCurrentValue
    private Double maxRadius;

    public NodeTask(Node node) {
        super(node);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPointsData = new ArrayList<>();
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
        Double maxRadiusCube = null;
        if (maxRadius != null) {
            maxRadiusCube = Math.pow(maxRadius, 2);
        }
        for (double theta = 0; theta <= maxTheta; theta += stepTheta) {

            double distanceFromCenterToPoint = 1 / Math.tan(theta * nodeModifier);

            final double radiusCosTheta = radius * Math.cos(theta) * distanceFromCenterToPoint;
            final double radiusSinTheta = radius * Math.sin(theta) * distanceFromCenterToPoint;
            double x = (U.getX() * radiusCosTheta) + (V.getX() * radiusSinTheta);
            double y = (U.getY() * radiusCosTheta) + (V.getY() * radiusSinTheta);
            double z = (U.getZ() * radiusCosTheta) + (V.getZ() * radiusSinTheta);

            final Vector fromCenterToPoint = new Vector(x, y, z);

            if (maxRadiusCube == null || maxRadiusCube >= fromCenterToPoint.lengthSquared()) {
                animationPointsData.add(new AnimationPointData(fromCenterToPoint));
            }
        }
        return animationPointsData;
    }
}
