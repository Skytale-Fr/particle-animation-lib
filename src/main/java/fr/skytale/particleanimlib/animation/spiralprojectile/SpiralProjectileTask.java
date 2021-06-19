package fr.skytale.particleanimlib.animation.spiralprojectile;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpiralProjectileTask extends ARoundAnimationTask<SpiralProjectile> {
    private Location target;
    private AAnimation spiral1;
    private AAnimation spiral2;
    private double step;
    private int taskId;

    //Evolving variables
    Vector start, end, stepVector;
    double distance;

    public SpiralProjectileTask(SpiralProjectile spiralProjectile) {
        super(spiralProjectile);
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    protected void init() {
        super.init();

        this.target = animation.getTarget();
        this.spiral1 = animation.getSpiral1();
        this.spiral2 = animation.getSpiral2();
        this.step = animation.getStep();

        start = location.toVector();
        end = target.toVector();
        stepVector = end.clone().subtract(start).normalize().multiply(step);
        distance = target.distance(location);
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (distance<=0) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        //Axe central
        Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
        mainParticle.getParticleBuilder(particleLocation).display();
        distance -= step;

        start.add(stepVector);
    }

    /*@Override
    public void run() {
        if (length >= distance) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }

        //Axe central
        Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
        mainParticle.getParticleBuilder(particleLocation).display();
        length += step;

        start.add(stepVector);
    }*/
}
