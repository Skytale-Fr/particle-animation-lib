package fr.skytale.particleanimlib.animation.animation.lightning;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Possible evolutions:
 * - randomly split the lightning at some points
 */
public class LightningTask extends AAnimationTask<Lightning> {

    private final Random random;
    private LinkedList<Vector> persistentPoints;

    public LightningTask(Lightning lightning) {
        super(lightning);
        persistentPoints = null;
        random = new Random();
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {

        List<AnimationPointData> animationPoints = new ArrayList<>();

        // Initialize animation points during first iteration
        if (persistentPoints == null) {
            initAnimationData();
        }

        double nbPointsByTicks = ((double) persistentPoints.size()) / animation.getTicksDuration();
        int nbRemainingTicks = animation.getTicksDuration() - iterationCount;
        double nbPointsToShow = Math.floor(nbPointsByTicks * nbRemainingTicks);

        double distanceBetweenParticles = animation.getDistanceBetweenPoints().getCurrentValue(iterationCount);
        if (persistentPoints.size() > 1) {
            Vector previousPoint = persistentPoints.get(0);
            for (int i = 1; i < nbPointsToShow && i < persistentPoints.size(); i++) {
                Vector currentPoint = persistentPoints.get(i);
                animationPoints.addAll(AnimationTaskUtils.getLinePoints(previousPoint, currentPoint, distanceBetweenParticles));
                previousPoint = currentPoint;
            }
        }
        return animationPoints;
    }

    @Override
    protected boolean shouldStop() {
        // Stop if no point to show anymore
        return super.shouldStop() || (persistentPoints != null && persistentPoints.isEmpty());
    }

    private void initAnimationData() {
        this.persistentPoints = new LinkedList<>();
        //Computing target location
        Vector originToTarget = animation.getTargetLocation().toVector().subtract(getCurrentIterationBaseLocation().toVector());

        Vector originToTargetNormalized = originToTarget.clone().normalize();

        double fromOriginToTargetLength = originToTarget.length();

        double fromOriginToCenterLength = fromOriginToTargetLength / 2;

        double minDistanceBetweenLightningAngles = animation.getMinDistanceBetweenLightningAngles();
        double maxDistanceBetweenLightningAngles = animation.getMaxDistanceBetweenLightningAngles();

        RotatableVector.Plane2D plane2DNormalToDirection = new RotatableVector(originToTargetNormalized).getPlane();


        //Add first point
        persistentPoints.add(new Vector(0, 0, 0));

        double dispersionAngle = animation.getDispersionAngle();

        double fromOriginToPreviousPointLength = 0.0;

        while (true) {
            double fromPreviousToCurrentPointLength = (
                    random.nextDouble() * (maxDistanceBetweenLightningAngles - minDistanceBetweenLightningAngles) +
                    minDistanceBetweenLightningAngles
            );
            double fromOriginToCurrentPointLength = fromOriginToPreviousPointLength + fromPreviousToCurrentPointLength;

            Vector currentPoint;
            Vector currentPointOnLine;
            boolean endReached = fromOriginToCurrentPointLength > fromOriginToTargetLength;

            //If the lightning is converging to a specific place, and if we will reach floor,
            // then we define that the floor location we will reach is the given target.
            if (endReached && animation.isConvergeToTarget()) {
                currentPoint = originToTarget;
            } else {
                //If the lightning is NOT converging, we prevent the lightning going further than the floor
                if (endReached) {
                    fromOriginToCurrentPointLength = fromOriginToTargetLength;
                }

                currentPointOnLine = originToTargetNormalized.clone().multiply(fromOriginToCurrentPointLength);
                //Computing radius
                double maxRadius;
                if ((!animation.isConvergeToTarget()) || fromOriginToCurrentPointLength < fromOriginToCenterLength) {
                    maxRadius = Math.tan(dispersionAngle) * fromOriginToCurrentPointLength;
                } else {
                    double fromTargetToCurrentPointLength = currentPointOnLine.length();
                    maxRadius = Math.tan(dispersionAngle) * fromTargetToCurrentPointLength;
                }
                double randomRadius = random.nextDouble() * maxRadius;

                //Computing theta
                double randomTheta = random.nextDouble() * 2 * Math.PI;

                //Compute the lightning next random point (circle equation with center: currentPointOnLine, plane U,V, random radius. Computing a single point with angle theta.
                double x = currentPointOnLine.getX() +
                           (plane2DNormalToDirection.u.getX() * randomRadius * Math.cos(randomTheta)) +
                           (plane2DNormalToDirection.v.getX() * randomRadius * Math.sin(randomTheta));
                double y = currentPointOnLine.getY() +
                           (plane2DNormalToDirection.u.getY() * randomRadius * Math.cos(randomTheta)) +
                           (plane2DNormalToDirection.v.getY() * randomRadius * Math.sin(randomTheta));
                double z = currentPointOnLine.getZ() +
                           (plane2DNormalToDirection.u.getZ() * randomRadius * Math.cos(randomTheta)) +
                           (plane2DNormalToDirection.v.getZ() * randomRadius * Math.sin(randomTheta));

                currentPoint = new Vector(
                        Math.round(x * 100) / 100.0,
                        Math.round(y * 100) / 100.0,
                        Math.round(z * 100) / 100.0);
            }
            persistentPoints.add(currentPoint);

            if (endReached) {
                break;
            }

            fromOriginToPreviousPointLength = fromOriginToCurrentPointLength;
        }
    }
}
