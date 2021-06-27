package fr.skytale.particleanimlib.animation.spiralprojectile;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SpiralProjectileTask extends ARoundAnimationTask<SpiralProjectile> {
    private Vector target;
    private AAnimation spiral1;
    private AAnimation spiral2;
    private double step;

    //Evolving variables
    Vector start, end, stepVector;
    double distance;

    public SpiralProjectileTask(SpiralProjectile spiralProjectile) {
        super(spiralProjectile);

        this.target = animation.getTarget().clone();
        this.spiral1 = (AAnimation) animation.getSpiral1().clone();
        this.spiral2 = (AAnimation) animation.getSpiral2().clone();
        this.step = animation.getStep();

        start = new Vector(0, 0, 0);//location.toVector();
        end = target;
        stepVector = end.clone().subtract(start).normalize().multiply(step);
        distance = target.length();

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (distance <= 0) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        //Axe central
        Location circleCenter = iterationBaseLocation.add(start.getX(), start.getY(), start.getZ());
        //Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
        mainParticle.getParticleBuilder(circleCenter).display();
        distance -= step;

        start.add(stepVector);
    }
}
