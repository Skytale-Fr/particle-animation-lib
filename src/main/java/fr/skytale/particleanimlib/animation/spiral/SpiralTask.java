package fr.skytale.particleanimlib.animation.spiral;

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
    double distance, theta, radiusCopy;
    Vector start, end, stepVector, directorVector, radiusVector;

    public SpiralTask(Spiral spiral) {
        super(spiral);
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    protected void init() {
        super.init();
        this.step = animation.getStep();
        this.target = animation.getTarget();
        this.growthSpeed = animation.getGrowthSpeed();

        theta = 0;
        radiusCopy = radius;

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
    public void show(Location iterationBaseLocation) {

        //Stop
        if (distance<=0) {
stopAnimation(taskId);
            return;
        }

        //Central axis
        Location circleCenter = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
        distance -= step;

        //Calculating radiusVector
        setRadiusVector();

        //Displaying particles
        Location particleLocationSpiral = circleCenter.clone().add(radiusVector);
        particleLocationSpiral = animation.rotateAroundAxis(particleLocationSpiral, directorVector, circleCenter, theta);
        mainParticle.getParticleBuilder(particleLocationSpiral).display();

        start.add(stepVector);
        theta += stepAngle;
        radiusCopy += growthSpeed;
        iterationCount++;
    }

    /*@Override
    public void run() {

        //Stop
        if (length >= distance) {
stopAnimation(taskId);
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
    }*/
}
