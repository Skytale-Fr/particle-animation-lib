package fr.skytale.particleanimlib.explodingsphere;

import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ExplodingSphereTask extends ARoundAnimationTask<ExplodingSphere> {
    private double growthSpeed;
    private int nbCircles;
    private double explosionLimit;
    private int taskId;

    //Evolving variables
    private double growth;
    private int iterationCount;

    public ExplodingSphereTask(ExplodingSphere animation) {
        super(animation);
        this.growthSpeed = animation.getGrowthSpeed();
        this.nbCircles = animation.getNbCircles();
        this.explosionLimit = animation.getExplosionLimit();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        growth = 1;
        iterationCount = 0;
        iterationCount = 0;
    }

    @Override
    public void run() {

        Location sphereCenter = animation.getBaseLocation();

        if (growth >= explosionLimit) {
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


        for (double i = 0; i < Math.PI; i += Math.PI / nbCircles) {
            double currentRadius = Math.sin(i) * radius * growth;
            double y = sphereCenter.getY() + Math.cos(i) * radius * growth;

            for (double j = 0; j < Math.PI * 2; j += stepAngle) {
                double x = sphereCenter.getX() + Math.cos(j) * currentRadius;
                double z = sphereCenter.getZ() + Math.sin(j) * currentRadius;

                mainParticle.getParticleBuilder(new Location(location.getWorld(), x, y, z)).display();
            }
        }
        growth += growthSpeed;
        iterationCount++;

    }
}
