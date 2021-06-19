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
    protected Vector moveVector;
    protected Integer moveFrequency;
    protected Vector moveStepVector;

    //Evolving variables
    protected int iterationCount;

    public AAnimationTask(T animation) {
        this.animation = animation;
        this.location = animation.getLocation();
        this.movingEntity = animation.getMovingEntity();
        this.relativeLocation = animation.getRelativeLocation();
        this.mainParticle = animation.getMainParticle();
        this.plugin = animation.getPlugin();
        this.ticksDuration = animation.getTicksDuration();
        this.showFrequency = animation.getShowFrequency();
        if (animation.getMoveVector() != null) {
            this.moveFrequency = animation.getMoveFrequency();
            this.moveVector = animation.getMoveVector();
            moveStepVector = moveVector.normalize().multiply(animation.getMoveStep());
        }

        init();
    }

    protected void init(){
        iterationCount = 0;
    }

    public final void run() {

        //if move specified
        if (moveFrequency != null && (moveFrequency == 0 || iterationCount % moveFrequency == 0)) {
            if (movingEntity == null) {
                location.add(moveStepVector);
            } else {
                relativeLocation.add(moveStepVector);
            }
        }
        Location iterationBaseLocation = movingEntity == null ? location : movingEntity.getLocation().clone().add(relativeLocation);

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
    public boolean hasDurationEnded(){
        return iterationCount>=ticksDuration;
    }

    public void stopAnimation(int taskId){
        Bukkit.getScheduler().cancelTask(taskId);
        if (animation.getCallback() != null) {
            animation.getCallback().run(animation);
        }
    }

    public void drawLine(Location point1, Location point2, double step) {
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(step);

        for (double length = 0; length < distance; p1.add(vector)) {

            Location particleLocation = new Location(point1.getWorld(), p1.getX(), p1.getY(), p1.getZ());
            mainParticle.getParticleBuilder(particleLocation).display();
            length += step;
        }
    }
}
