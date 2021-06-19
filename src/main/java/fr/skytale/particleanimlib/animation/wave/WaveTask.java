package fr.skytale.particleanimlib.animation.wave;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WaveTask extends ARoundAnimationTask<Wave> {

    private double maxRadius;
    private double step;
    private AAnimation circleAnim;
    private int taskId;

    //Evolving variables
    double currentRadius;

    public WaveTask(Wave wave) {
        super(wave);
        init();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    protected void init() {
        super.init();
        this.maxRadius = animation.getMaxRadius();
        this.step = animation.getStep();
        this.circleAnim = animation.getCircleAnim();
        currentRadius = radius;
    }

    @Override
    public void show(Location iterationBaseLocation) {
        Location waveCenter = iterationBaseLocation;

        if (currentRadius >= maxRadius) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        ARoundAnimation circle = (ARoundAnimation) circleAnim;

        //Updating radius
        circle.setRadius(currentRadius);

        //Updating height
        double y = waveCenter.getY() + (2 * Math.exp(-0.1 * (39 / (maxRadius - radius) * currentRadius)) * Math.sin(currentRadius)) + 1;
        circle.setLocation(new Location(waveCenter.getWorld(), circle.getLocation().getX(), y, circle.getLocation().getZ()));

        circle.show();

        currentRadius += step;
    }

    /*@Override
    public void run() {
        Location waveCenter = animation.getBaseLocation();

        if (currentRadius >= maxRadius) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }

        ARoundAnimation circle = (ARoundAnimation) circleAnim;

        //Updating radius
        circle.setRadius(currentRadius);

        //Updating height
        double y = waveCenter.getY() + (2 * Math.exp(-0.1 * (39 / (maxRadius - radius) * currentRadius)) * Math.sin(currentRadius)) + 1;
        circle.setLocation(new Location(waveCenter.getWorld(), circle.getLocation().getX(), y, circle.getLocation().getZ()));

        circle.show();

        currentRadius += step;
        iterationCount++;
    }*/
}
