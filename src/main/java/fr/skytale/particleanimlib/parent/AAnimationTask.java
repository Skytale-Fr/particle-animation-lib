package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {
    protected T animation;

    protected Location location;
    protected Entity movingEntity;
    protected Vector relativeLocation;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected int showFrequency;
    protected Integer moveFrequency;
    protected Vector moveStepVector;
    protected Integer taskId;

    //Evolving variables
    protected int iterationCount;

    public AAnimationTask(T animation) {
        this.animation = animation;
        this.iterationCount = 0;
        this.location = animation.getLocation() == null ? null : animation.getLocation().clone();
        this.movingEntity = animation.getMovingEntity();
        this.relativeLocation = animation.getRelativeLocation() == null ? null : animation.getRelativeLocation().clone();
        this.mainParticle = new ParticleTemplate(animation.getMainParticle());
        this.plugin = animation.getPlugin();
        this.ticksDuration = animation.getTicksDuration();
        this.showFrequency = animation.getShowFrequency();
        if (animation.getMoveStepVector() != null) {
            this.moveFrequency = (animation.getMoveFrequency() == null ? null : animation.getMoveFrequency());
            this.moveStepVector = animation.getMoveStepVector().clone();
        }
    }

    public final void run() {

        boolean hasMovingEntity = movingEntity == null;
        //if move specified
        if (moveFrequency != null && (moveFrequency == 0 || iterationCount % moveFrequency == 0)) {
            if (hasMovingEntity) {
                location.add(moveStepVector);
            } else {
                relativeLocation.add(moveStepVector);
            }
        }
        Location iterationBaseLocation = hasMovingEntity ? location.clone() :
                (relativeLocation != null ? movingEntity.getLocation().clone().add(relativeLocation) : movingEntity.getLocation().clone());

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }
        show(iterationBaseLocation);
        iterationCount++;
    }

    public abstract void show(Location iterationBaseLocation);

    /***Methods used in subclasses***/
    public boolean hasDurationEnded() {
        return iterationCount >= ticksDuration;
    }

    protected void stopAnimation() {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
        }
        if (animation.getCallback() != null) {
            animation.getCallback().run(animation);
        }
    }

    public void drawLine(Location point1, Location point2, double step) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.toVector().subtract(point1.toVector()).normalize().multiply(step);
        Location currentLoc = point1.clone();
        for (double length = 0; length < distance; currentLoc.add(stepVector)) {
            mainParticle.getParticleBuilder(currentLoc).display();
            length += step;
        }
    }

    protected Vector computeRadiusVector(Vector normalVector, double radius) {
        /*Let directorVector=(a,b,c).
        Then the equation of the plane containing the point (0,0,0) with directorVector as normal vector is ax + by + cz = 0.
        We want to find the vector radiusVector belonging to the plane*/
        double a = normalVector.getX();
        double b = normalVector.getY();
        double c = normalVector.getZ();

        Vector radiusVector;

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

        return radiusVector.normalize().multiply(radius);
    }
}
