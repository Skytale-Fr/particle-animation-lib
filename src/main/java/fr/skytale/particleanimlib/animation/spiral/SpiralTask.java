package fr.skytale.particleanimlib.animation.spiral;

import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpiralTask extends ARoundAnimationTask<Spiral> {
    private double step;
    private Vector target;
    private double growthSpeed;
    private int taskId;

    Vector end, stepVector, directorVector;
    //Evolving variables
    double distance, theta;
    Vector start, radiusVector;

    public SpiralTask(Spiral spiral) {
        super(spiral);

        this.step = animation.getStep();
        this.target = animation.getTarget().clone();
        this.growthSpeed = animation.getGrowthSpeed();

        theta = 0;

        start = new Vector(0, 0, 0);
        end = target;
        stepVector = end.clone().subtract(start).normalize().multiply(step);
        directorVector = end.clone().subtract(start);
        distance = target.length();

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        //Stop
        if (distance <= 0) {
            stopAnimation();
            return;
        }

        //Central axis
        Location circleCenter = iterationBaseLocation.add(start.getX(), start.getY(), start.getZ());
        distance -= step;

        //Calculating radiusVector
        this.radiusVector = computeRadiusVector(directorVector, radius);

        //Displaying particles
        Location particleLocationSpiral = circleCenter.clone().add(radiusVector);
        particleLocationSpiral = animation.rotateAroundAxis(particleLocationSpiral, directorVector, circleCenter, theta);
        mainParticle.getParticleBuilder(particleLocationSpiral).display();

        start.add(stepVector);
        theta += stepAngle;
        radius += growthSpeed;
        iterationCount++;
    }
}
