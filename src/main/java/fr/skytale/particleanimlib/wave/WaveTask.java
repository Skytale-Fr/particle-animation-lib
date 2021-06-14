package fr.skytale.particleanimlib.wave;

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
    int iterationCount;

    public WaveTask(Wave wave) {
        super(wave);
        this.maxRadius = wave.getMaxRadius();
        this.step = wave.getStep();
        this.circleAnim = wave.getCircleAnim();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        currentRadius = radius;
        iterationCount = 0;
    }

    @Override
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
    }
}
