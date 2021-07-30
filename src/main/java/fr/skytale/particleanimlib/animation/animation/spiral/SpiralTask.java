package fr.skytale.particleanimlib.animation.animation.spiral;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class SpiralTask extends AAnimationTask<Spiral> {

    private final boolean followEntity;
    private final LinkedList<Set<PointData>> trailPointsPerIteration;
    private Vector entityRelativeLocation;
    private Location absoluteLocation;
    private double spiralParticlesChangingAngle;
    private Set<PointData> currentIterationPoints;

    public SpiralTask(Spiral spiral) {
        super(spiral);
        followEntity = animation.getDirection().getType() == AnimationDirection.Type.MOVE_VECTOR && animation.getPosition().getType() == APosition.Type.ENTITY;
        spiralParticlesChangingAngle = 0;
        trailPointsPerIteration = new LinkedList<>();

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        // --- Stop if max duration reached
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        // --- Computing the current animation location
        Location animationCurrentLocation;

        if (followEntity) {
            /* If the spiral follows an entity, its current location (animationCurrentLocation) will be :
                animation.getPosition().getMovingEntity().getLocation()
                          .add(animation.getPosition().getRelativeLocation())
                          .add(entityRelativeLocation);
                entityRelativeLocation will evolve over time.
            */
            if (entityRelativeLocation == null) {
                //First iteration : initialization
                entityRelativeLocation = new Vector(0, 0, 0);

            }
            animationCurrentLocation = iterationBaseLocation.add(entityRelativeLocation);
        } else {
            //If the spiral does not follow an entity, it's current location will depend of the previous show location
            if (absoluteLocation == null) {
                //First iteration : initialization
                absoluteLocation = iterationBaseLocation;
            }
            animationCurrentLocation = absoluteLocation;
        }

        // --- Retrieves move Vector
        AnimationDirection.MoveData nextMoveData = animation.getDirection().getMoveVector(animationCurrentLocation, iterationCount);

        // --- Handling errors (if target can't be reached anymore)
        if (nextMoveData.hasError)
            stopAnimation(false);

        // --- Display trailing particles
        Integer nbTrailingParticles = animation.getNbTrailingParticles().getCurrentValue(iterationCount);
        displayTrail(nbTrailingParticles);

        // --- Reinitialize the Set that contains this iteration particles
        currentIterationPoints = new HashSet<>();

        // --- show the central particle (and add it to the Set to be able to show the particle again within the spiral trail during a future iteration)
        displayPoint(animationCurrentLocation, nextMoveData.move, true);


        //Calculating radiusVector
        Vector directorVector = nextMoveData.move.clone().normalize();
        double radius = animation.getRadius().getCurrentValue(iterationCount);
        Vector radiusVector = computeRadiusVector(directorVector, radius);

        //Calculating each spiral particle locations
        Location firstSpiralParticleLocationBeforeRotation = animationCurrentLocation.clone().add(radiusVector);

        Integer nbSpirals = animation.getNbSpiral().getCurrentValue(iterationCount);
        double spiralParticlesGapAngle = 2 * Math.PI / nbSpirals;
        for (int i = 0; i < nbSpirals; i++) {
            double currentSpiralParticleAngle = i * spiralParticlesGapAngle + spiralParticlesChangingAngle;
            Location currentSpiralParticleLocation = rotateAroundAxis(firstSpiralParticleLocationBeforeRotation, directorVector, animationCurrentLocation, currentSpiralParticleAngle);
            // --- show each spiral particle (and add it to the Set to be able to show the particle again within the spiral trail during a future iteration)
            displayPoint(currentSpiralParticleLocation, currentSpiralParticleLocation.toVector().subtract(animationCurrentLocation.toVector()), false);
        }


        // --- Stop if target reached
        if (nextMoveData.willReachTarget) {
            stopAnimation();
            return;
        }

        // --- Prepare the next iteration
        spiralParticlesChangingAngle += animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);

        if (followEntity) {
            entityRelativeLocation.add(nextMoveData.move);
        } else {
            absoluteLocation.add(nextMoveData.move);
        }

        // We add the new trail particles at the beginning of the list. So bigger index = older
        trailPointsPerIteration.add(0, currentIterationPoints);
    }

    private void displayTrail(Integer nbTrailingParticles) {

        /* Usage example :
            trailParticlesPerIteration = (newest) 0 , 1 , 2 , 3 , 4 (oldest)
            trailParticlesPerIteration.size() = 5
            nbTrailingParticles = 3
            ==> Show 0 , 1 , 2
            ==> Remove 3 , 4 , 5
         */
        Iterator<Set<PointData>> it = trailPointsPerIteration.iterator();
        int i = 0;
        while (it.hasNext()) {
            Set<PointData> iterationParticles = it.next();
            if (i < nbTrailingParticles) {
                iterationParticles.forEach(this::showPoint);
            } else {
                it.remove();
            }
            i++;
        }
    }

    private void displayPoint(Location pointLocation, Vector fromCenterToPoint, boolean isCentral) {
        APointDefinition pointDefinition;
        if (isCentral) {
            pointDefinition = animation.getCentralPointDefinition();
            if (pointDefinition == null) return;
        } else {
            pointDefinition = animation.getPointDefinition();
        }
        PointData pointData = new PointData(pointDefinition, pointLocation.clone(), fromCenterToPoint.clone().normalize());

        // Add the built particle to the currentIterationParticles Set to be able to show the particle again within the spiral trail during a future iteration
        currentIterationPoints.add(pointData);

        // Show the particle/ISubAnimation
        showPoint(pointData);
    }

    public void showPoint(PointData pointData) {
        showPoint(pointData.pointDefinition, pointData.pointLocation, pointData.fromCenterToPoint);
    }

    public static class PointData {
        public APointDefinition pointDefinition;
        public Location pointLocation;
        public Vector fromCenterToPoint;

        public PointData(APointDefinition pointDefinition, Location pointLocation, Vector fromCenterToPoint) {
            this.pointDefinition = pointDefinition;
            this.pointLocation = pointLocation;
            this.fromCenterToPoint = fromCenterToPoint;
        }
    }
}
