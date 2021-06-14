package fr.skytale.particleanimlib.spiralprojectile;

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
    int iterationCount;
    Vector start, end, stepVector;
    double length, distance;

    public SpiralProjectileTask(SpiralProjectile spiralProjectile) {
        super(spiralProjectile);
        this.target = spiralProjectile.getTarget();
        this.spiral1 = spiralProjectile.getSpiral1();
        this.spiral2 = spiralProjectile.getSpiral2();
        this.step = spiralProjectile.getStep();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        iterationCount = 0;
        length = 0;

        start = location.toVector();
        end = target.toVector();
        stepVector = end.clone().subtract(start).normalize().multiply(step);
        distance = target.distance(location);
    }

    @Override
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
    }
}
