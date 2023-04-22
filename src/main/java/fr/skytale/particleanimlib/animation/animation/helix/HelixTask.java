package fr.skytale.particleanimlib.animation.animation.helix;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.RotatableVector;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.animation.parent.task.AnimationTaskUtils;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ForceUpdatePointsConfiguration(alwaysUpdate = true)
public class HelixTask extends AAnimationTask<Helix> {

    @IVariableCurrentValue
    private Double radius;

    @IVariableCurrentValue
    private Integer nbHelix;

    @IVariableCurrentValue
    private Double helixAngle;

    @IVariableCurrentValue
    private Integer nbTrailingHelixPoint;

    @IVariableCurrentValue
    private Integer nbTrailingCentralPoint;

    private double helixAngleSum;

    private final TrailData trailData;

    public HelixTask(Helix helix) {
        super(helix);
        trailData = new TrailData();
        helixAngleSum = 0;
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {

        // Create the central point
        AnimationPointData currentIterationCentralPoint = new AnimationPointData(new Vector(), pointDefinition -> animation.getCentralPointDefinition());

        // The Set that contains this iteration helix points
        List<AnimationPointData> currentIterationHelixPoints = new ArrayList<>();

        // Calculating radiusVector according to currentIterationMoveVector
        if (nbHelix > 0) {
            final Vector directorVector = getCurrentIterationMove().getMove();
            Vector radiusVector = AnimationTaskUtils.computeRadiusVector(directorVector.clone().normalize(), radius);

            // Rotate radius vector according to the current value of helixAnglePerIteration to obtain the first helix point vector
            RotatableVector firstPoint = new RotatableVector(radiusVector);
            if (helixAngleSum != 0) {
                firstPoint = firstPoint.rotateAroundAxis(directorVector, helixAngleSum);
            }
            currentIterationHelixPoints.add(new AnimationPointData(firstPoint));

            //Calculating each helix particle Vector
            double helixParticlesGapAngle = 2 * Math.PI / nbHelix;
            for (int i = 1; i < nbHelix; i++) {
                // helixAnglePerIteration is used to rotate the helix points between each iteration
                // helixParticlesGapAngle is the angle between two point of the same iteration
                double currentHelixPointAngle = i * helixParticlesGapAngle;
                Vector currentHelixPoint = new RotatableVector(firstPoint).rotateAroundAxis(directorVector, currentHelixPointAngle);
                // --- show each helix particle (and add it to the Set to be able to show the particle again within the helix trail during a future iteration)
                currentIterationHelixPoints.add(new AnimationPointData(currentHelixPoint));
            }
        }

        // Create a new trail data
        TrailIterationPoints currentIterationTrailPoints = new TrailIterationPoints(currentIterationCentralPoint, currentIterationHelixPoints);

        // Prepare the next iteration
        helixAngleSum += helixAngle;

        // Update the trail, add the new point of the animation and then compute an ordered list of AnimationPointData to show
        return trailData.computePointsAndUpdateTrail(
                currentIterationTrailPoints,
                nbTrailingHelixPoint,
                nbTrailingCentralPoint,
                getCurrentIterationMove().getMove());
    }

    private static class TrailData {
        private List<TrailIterationPoints> trailPointsPerIteration;

        public TrailData() {
            trailPointsPerIteration = new LinkedList<>();
        }

        public List<AnimationPointData> computePointsAndUpdateTrail(TrailIterationPoints newTrailIterationPoints, int nbTrailingHelixPoint, int nbTrailingCentralPoint, Vector lastAnimationMove) {
            // apply the inverted move offset to undertake the fact the trail must stay behind instead of move with the animation.
            Vector invertedAnimationMove = lastAnimationMove.clone().multiply(-1);
            trailPointsPerIteration.forEach(trailIterationPoints -> trailIterationPoints.moveTrailPoints(invertedAnimationMove));

            // Add this iteration data to the trail
            trailPointsPerIteration.add(0, newTrailIterationPoints);

            // Purge the list of TrailIterationPoints to avoid keeping useless data
            int maxNbTrailingIterationData = Math.min(trailPointsPerIteration.size(), Math.max(nbTrailingCentralPoint, nbTrailingHelixPoint));
            this.trailPointsPerIteration = trailPointsPerIteration.subList(0, maxNbTrailingIterationData);


            // Add the central points
            List<AnimationPointData> animationPoints = new ArrayList<>();
            for (int i = 0; i < nbTrailingCentralPoint && i < trailPointsPerIteration.size(); i++) {
                animationPoints.add(trailPointsPerIteration.get(i).getCentralPoint());
            }
            // Add the helix points
            for (int i = 0; i < nbTrailingHelixPoint && i < trailPointsPerIteration.size(); i++) {
                animationPoints.addAll(trailPointsPerIteration.get(i).getHelixPoints());
            }

            return animationPoints;
        }
    }

    private static class TrailIterationPoints {
        private AnimationPointData centralPoint;
        private List<AnimationPointData> helixPoints;

        public TrailIterationPoints(AnimationPointData centralPoint, List<AnimationPointData> helixPoints) {
            this.centralPoint = centralPoint;
            this.helixPoints = helixPoints;
        }

        public AnimationPointData getCentralPoint() {
            return centralPoint;
        }

        public List<AnimationPointData> getHelixPoints() {
            return helixPoints;
        }

        public void moveTrailPoints(Vector invertedAnimationMoveVector) {
            centralPoint = new AnimationPointData(centralPoint.getFromCenterToPoint().add(invertedAnimationMoveVector), centralPoint.getPointDefinitionModifier());
            helixPoints = helixPoints.stream()
                    .map(helixPoint -> new AnimationPointData(helixPoint.getFromCenterToPoint().add(invertedAnimationMoveVector), helixPoint.getPointDefinitionModifier()))
                    .collect(Collectors.toList());
        }
    }
}
