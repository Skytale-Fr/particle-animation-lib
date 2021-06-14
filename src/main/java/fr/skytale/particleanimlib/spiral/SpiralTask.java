package fr.skytale.particleanimlib.spiral;

import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpiralTask extends ARoundAnimationTask<Spiral> {
    private double step;
    private Location target;
    private double growthSpeed;
    private int taskId;

    //Evolving variables
    double distance, theta, length, radiusCopy;
    Vector start, end, stepVector, directorVector, radiusVector;
    int iterationCount;

    public SpiralTask(Spiral spiral) {
        super(spiral);
        this.step = spiral.getStep();
        this.target = spiral.getTarget();
        this.growthSpeed = spiral.getGrowthSpeed();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        theta = 0;
        length = 0;
        radiusCopy = radius;
        iterationCount = 0;

        start = location.toVector();
        end = target.toVector();
        stepVector = end.clone().subtract(start).normalize().multiply(step);
        directorVector = end.clone().subtract(start);
        distance = target.distance(location);
    }

    private void setRadiusVector() {
        /*Let directorVector=(a,b,c).
        Then the equation of the plane containing the point (0,0,0) with directorVector as normal vector is ax + by + cz = 0.
        We want to find the vector radiusVector belonging to the plane*/
        double a = directorVector.getX();
        double b = directorVector.getY();
        double c = directorVector.getZ();

        if (a == 0) {
            if (b == 0)
                radiusVector = new Vector(1, 1, 0);
            else if (c == 0)
                radiusVector = new Vector(1, 0, 1);
            else
                radiusVector = new Vector(1, 1, -b / c);
        } else if (b == 0) {
            if (c == 0)
                radiusVector = new Vector(0, 1, 1);
            else
                radiusVector = new Vector(1, 1, -a / c);
        } else if (c == 0)
            radiusVector = new Vector(1, -b / a, 1);
        else
            radiusVector = new Vector(1, 1, (-a - b) / c);

        radiusVector.normalize().multiply(radiusCopy);
    }

    @Override
    public void run() {

        //Stop
        if (length >= distance) {
            Bukkit.getScheduler().cancelTask(taskId);
            if (animation.getCallback() != null) {
                animation.getCallback().run(animation);
            }
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }

        //Central axis
        Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
        length += step;

        //Calculating radiusVector
        setRadiusVector();

        //Displaying particles
        Location particleLocationSpiral = particleLocation.clone().add(radiusVector);
        particleLocationSpiral = animation.rotateAroundAxis(particleLocationSpiral, directorVector, particleLocation, theta);
        mainParticle.getParticleBuilder(particleLocationSpiral).display();

        start.add(stepVector);
        theta += stepAngle;
        radiusCopy += growthSpeed;
        iterationCount++;
    }
}
