package fr.skytale.particleanimlib.animation.animation.randompoints;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Possible evolutions:
 * - bracing angle (avoiding instantaneous 180° turn)
 * - asynchronous direction change (the particles currently change their direction at the same time)
 * - world collision (avoid entering blocks)
 * - Add a general direction
 * - Avoid exiting sphere:
 *   - Either the particle that exit the sphere will disappear and a new one will appear at a random place
 *   - Either the particle can not move outside the sphere
 */
public class RandomPointsTask extends AAnimationTask<RandomPoints> {

    @IVariableCurrentValue
    private Integer directionChangePeriod;

    @IVariableCurrentValue
    private Float speed;

    private boolean pointsSpawned = false;

    private Random random = new Random();

    private List<RandomPointData> randomPointsData;

    public RandomPointsTask(RandomPoints animation) {
        super(animation);
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {

        //If first display
        if (!pointsSpawned) {
            this.randomPointsData = new ArrayList<>();
            // Finding random points in sphere
            for (int i = 0; i < animation.getNbPoints(); i++) {
                double u = random.nextDouble();
                double v = random.nextDouble();
                double theta = 2 * Math.PI * u;
                double phi = Math.acos(2 * v - 1);

                double x = animation.getRadius() * Math.sin(phi) * Math.cos(theta);
                double y = animation.getRadius() * Math.sin(phi) * Math.sin(theta);
                double z = animation.getRadius() * Math.cos(phi);

                this.randomPointsData.add(new RandomPointData(new Vector(x, y, z), getRandomDirection()));
            }
            pointsSpawned = true;

        } else {
            //Direction change
            if (iterationCount % directionChangePeriod == 0) {
                this.randomPointsData.forEach(randomPointData -> randomPointData.nextDirection = getRandomDirection());
            }

            //Compute next point according to direction and speed
            this.randomPointsData.forEach(randomPointData ->
                    randomPointData.relativePosition = randomPointData.relativePosition.clone()
                            .add(randomPointData.nextDirection.clone().multiply(speed)));
        }

        return randomPointsData.stream()
                .map(randomPointData -> new AnimationPointData(randomPointData.relativePosition.clone()))
                .collect(Collectors.toList());
    }

    private Vector getRandomDirection() {
        Vector direction = new Vector(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
        return direction.normalize();
    }

    protected static class RandomPointData {
        public Vector relativePosition;
        public Vector nextDirection;

        public RandomPointData(Vector relativePosition, Vector nextDirection) {
            this.relativePosition = relativePosition;
            this.nextDirection = nextDirection;
        }
    }

    @Override
    protected boolean shouldUpdatePoints() {
        return speed != 0;
    }
}
