package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CircleTask extends AAnimationTask<Circle> {

    @IVariableCurrentValue
    private Double radius;

    @IVariableCurrentValue
    private Integer nbPoints;

    @IVariableCurrentValue
    private Double angleBetweenEachPoint;

    public CircleTask(Circle circle) {
        super(circle);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        List<AnimationPointData> animationPoints = new ArrayList<>();
        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * angleBetweenEachPoint;

            final double radiusCosTheta = radius * Math.cos(theta);
            final double radiusSinTheta = radius * Math.sin(theta);
            double x = U.getX() * radiusCosTheta + V.getX() * radiusSinTheta;
            double y = U.getY() * radiusCosTheta + V.getY() * radiusSinTheta;
            double z = U.getZ() * radiusCosTheta + V.getZ() * radiusSinTheta;

            animationPoints.add(new AnimationPointData(new Vector(x, y, z)));
        }

        return animationPoints;
    }

    public double getCurrentRadius() {
        return radius;
    }
}
