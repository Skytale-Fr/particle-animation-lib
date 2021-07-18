package fr.skytale.particleanimlib.animation.animation.lighting;

import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.Random;

public class LightningTask extends AAnimationTask<Lightning> {

    private final Random random;
    private LinkedList<PointData> persistentPoints;

    public LightningTask(Lightning lightning) {
        super(lightning);
        persistentPoints = null;
        random = new Random();
        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        // --- Stop if max duration reached
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }


        // --- Initialize animation points during first iteration;
        if (persistentPoints == null) {
            initAnimationData(iterationBaseLocation);
        }

        // --- Stop if no point to show anymore
        int nbPoints = persistentPoints.size();
        if (nbPoints == 0) {
            stopAnimation();
            return;
        }

        // --- Show each lines or sub animations

        APointDefinition pointDefinition = animation.getPointDefinition();

        double nbPointsByTicks = ((double) persistentPoints.size()) / animation.getTicksDuration();
        int nbRemainingTicks = animation.getTicksDuration() - iterationCount;
        double nbPointsToShow = Math.floor(nbPointsByTicks * nbRemainingTicks);

        if (pointDefinition.hasSubAnimation()) {
            for (int i = 0; i < nbPointsToShow && i < persistentPoints.size(); i++) {
                PointData pointData = persistentPoints.get(i);
                showPoint(pointDefinition, pointData.pointLocation.clone(), pointData.directionToReachPoint.clone());
            }
        } else {
            double distanceBetweenParticles = animation.getDistanceBetweenPoints().getCurrentValue(iterationCount);
            if (persistentPoints.size() > 1) {
                PointData previousValue = persistentPoints.get(0);
                for (int i = 1; i < nbPointsToShow && i < persistentPoints.size(); i++) {
                    PointData currentValue = persistentPoints.get(i);
                    drawLine(previousValue.pointLocation.clone(), currentValue.pointLocation.clone(), distanceBetweenParticles, pointDefinition.clone());
                    previousValue = currentValue;
                }
            }
        }
    }

    private void initAnimationData(Location iterationBaseLocation) {
        this.persistentPoints = new LinkedList<>();
        //Computing target location
        Location targetLocation;
        switch (animation.getDirection().getType()) {
            case TARGET_ENTITY:
                targetLocation = animation.getDirection().getTargetEntity().getLocation().clone();
                break;
            case TARGET_LOCATION:
                targetLocation = animation.getDirection().getTargetLocation().getCurrentValue(0).clone();
                break;
            case MOVE_VECTOR:
                Vector maxLengthVector = animation.getDirection().getMoveVector().getCurrentValue(0);
                maxLengthVector.normalize().multiply(animation.getMaxDistance());
                targetLocation = iterationBaseLocation.clone().add(maxLengthVector);
                break;
            default:
                throw new IllegalStateException("this direction type is not supported");
        }

        Vector originToTarget = targetLocation.toVector().subtract(iterationBaseLocation.toVector());

        Vector originToTargetNormalized = originToTarget.clone().normalize();

        double fromOriginToTargetLength = originToTarget.length();

        double fromOriginToCenterLength = fromOriginToTargetLength / 2;

        double minDistanceBetweenLightingAngles = animation.getMinDistanceBetweenLightingAngles();
        double maxDistanceBetweenLightingAngles = animation.getMaxDistanceBetweenLightingAngles();

        RotatableVector.Plane2D plane2DNormalToDirection = new RotatableVector(originToTargetNormalized).getPlane();


        //Add first point
        persistentPoints.add(new PointData(iterationBaseLocation.clone(), originToTargetNormalized));

        double dispersionAngle = animation.getDispersionAngle();


        double fromOriginToPreviousPointLength = 0.0;
        Location previousPoint = iterationBaseLocation;

        while (true) {
            double fromPreviousToCurrentPointLength = (random.nextDouble() * (maxDistanceBetweenLightingAngles - minDistanceBetweenLightingAngles) + minDistanceBetweenLightingAngles);
            double fromOriginToCurrentPointLength = fromOriginToPreviousPointLength + fromPreviousToCurrentPointLength;

            Location currentPoint;
            Location currentPointOnLine;
            boolean endReached = fromOriginToCurrentPointLength > fromOriginToTargetLength;

            if (endReached && animation.isConvergeToTarget()) {
                currentPoint = targetLocation;
                currentPointOnLine = targetLocation;
            } else {
                if (endReached) {
                    fromOriginToCurrentPointLength = fromOriginToTargetLength;
                }

                currentPointOnLine = iterationBaseLocation.clone().add(originToTargetNormalized.clone().multiply(fromOriginToCurrentPointLength));
                //Computing radius
                double maxRadius;
                if ((!animation.isConvergeToTarget()) || fromOriginToCurrentPointLength < fromOriginToCenterLength) {
                    maxRadius = Math.tan(dispersionAngle) * fromOriginToCurrentPointLength;
                } else {
                    double fromTargetToCurrentPointLength = currentPointOnLine.toVector().subtract(targetLocation.toVector()).length();
                    maxRadius = Math.tan(dispersionAngle) * fromTargetToCurrentPointLength;
                }
                double randomRadius = random.nextDouble() * maxRadius;

                //Computing theta
                double randomTheta = random.nextDouble() * 2 * Math.PI;

                //Compute the lightning next random point (circle equation with center: currentPointOnLine, plane U,V, random radius. Computing a single point with angle theta.
                double x = currentPointOnLine.getX() + (plane2DNormalToDirection.u.getX() * randomRadius * Math.cos(randomTheta)) + (plane2DNormalToDirection.v.getX() * randomRadius * Math.sin(randomTheta));
                double y = currentPointOnLine.getY() + (plane2DNormalToDirection.u.getY() * randomRadius * Math.cos(randomTheta)) + (plane2DNormalToDirection.v.getY() * randomRadius * Math.sin(randomTheta));
                double z = currentPointOnLine.getZ() + (plane2DNormalToDirection.u.getZ() * randomRadius * Math.cos(randomTheta)) + (plane2DNormalToDirection.v.getZ() * randomRadius * Math.sin(randomTheta));

                currentPoint = new Location(iterationBaseLocation.getWorld(),
                        Math.round(x * 100) / 100.0,
                        Math.round(y * 100) / 100.0,
                        Math.round(z * 100) / 100.0);
            }
            persistentPoints.add(new PointData(currentPoint, currentPoint.toVector().subtract(previousPoint.toVector())));

            if (endReached) {
                break;
            }

            fromOriginToPreviousPointLength = fromOriginToCurrentPointLength;
            previousPoint = currentPoint;
        }
    }

    public static class PointData {
        //Each location where a particle/SubAnimation must be shown
        public Location pointLocation;
        //Used for some sub animation showing (those who requires a vector)
        public Vector directionToReachPoint;

        public PointData(Location pointLocation, Vector directionToReachPoint) {
            this.pointLocation = pointLocation;
            this.directionToReachPoint = directionToReachPoint;
        }
    }
}
