package fr.skytale.particleanimlib.animation.explodingsphere;

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

    public ExplodingSphereTask(ExplodingSphere animation) {
        super(animation);

        this.growthSpeed = animation.getGrowthSpeed();
        this.nbCircles = animation.getNbCircles();
        this.explosionLimit = animation.getExplosionLimit();
        this.growth = 1;

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        Location sphereCenter = iterationBaseLocation;

        if (growth >= explosionLimit) {
            stopAnimation(taskId);
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
    }
}
