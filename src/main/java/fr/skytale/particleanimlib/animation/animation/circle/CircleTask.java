package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CircleTask extends AAnimationTask<Circle> {
    private double angleBetweenEachPoint;
    private double radius;
    private int nbPoints;

    public CircleTask(Circle circle) {
        super(circle);
        startTask();
    }

    @Override
    protected boolean hasAnimationPointsChanged() {
        IVariable.ChangeResult<Double> angleBetweenEachPointChangeResult = animation.getAngleBetweenEachPoint().willChange(iterationCount, angleBetweenEachPoint);
        angleBetweenEachPoint = angleBetweenEachPointChangeResult.getNewValue();
        IVariable.ChangeResult<Double> radiusChangeResult = animation.getRadius().willChange(iterationCount, radius);
        radius = radiusChangeResult.getNewValue();
        IVariable.ChangeResult<Integer> nbPointsChangeResult = animation.getNbPoints().willChange(iterationCount, nbPoints);
        nbPoints = nbPointsChangeResult.getNewValue();

        return angleBetweenEachPointChangeResult.hasChanged() || radiusChangeResult.hasChanged() || nbPointsChangeResult.hasChanged();
    }

    @Override
    protected List<Vector> computeAnimationPoints() {
        List<Vector> animationPoints = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex++) {
            double theta = pointIndex * angleBetweenEachPoint;

            final double radiusCosTheta = radius * Math.cos(theta);
            final double radiusSinTheta = radius * Math.sin(theta);
            double x = U.getX() * radiusCosTheta + V.getX() * radiusSinTheta;
            double y = U.getY() * radiusCosTheta + V.getY() * radiusSinTheta;
            double z = U.getZ() * radiusCosTheta + V.getZ() * radiusSinTheta;

            animationPoints.add(new Vector(x, y, z));
        }

        return animationPoints;
    }

    public double getCurrentRadius() {
        return radius;
    }
}
