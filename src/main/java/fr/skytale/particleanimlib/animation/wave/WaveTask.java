package fr.skytale.particleanimlib.animation.wave;

import fr.skytale.particleanimlib.animation.parent.ARoundAnimation;
import fr.skytale.particleanimlib.animation.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WaveTask extends ARoundAnimationTask<Wave> {

    private double maxRadius;
    private double step;
    private ARoundAnimation anim;

    //Evolving variables
    double currentRadius;

    public WaveTask(Wave wave) {
        super(wave);
        this.maxRadius = animation.getMaxRadius();
        this.step = animation.getStep();
        this.anim = (ARoundAnimation) animation.getCircleAnim().clone();
        currentRadius = radius;
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        Location waveCenter = iterationBaseLocation;

        if (currentRadius >= maxRadius) {
            stopAnimation();
            return;
        }

        //Updating radius
        anim.setRadius(currentRadius);

        //Updating height
        double y = waveCenter.getY() + (2 * Math.exp(-0.1 * (39 / (maxRadius - radius) * currentRadius)) * Math.sin(currentRadius)) + 1;
        anim.setLocation(new Location(waveCenter.getWorld(), anim.getLocation().getX(), y, anim.getLocation().getZ()));

        anim.show();

        currentRadius += step;
    }

}
