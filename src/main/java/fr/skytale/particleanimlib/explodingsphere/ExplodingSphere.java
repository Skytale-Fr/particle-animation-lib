package fr.skytale.particleanimlib.explodingsphere;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExplodingSphere extends ARoundAnimation {
    private double growthSpeed;
    private int nbCircles;
    private double explosionLimit;

    @Override
    public void show(Player player) {
        Location sphereCenter;

        if(isFixedLocation())
            sphereCenter = location.clone();
        else
            sphereCenter = movingEntity.getLocation().clone().add(relativeLocation);

        new BukkitRunnable() {
            double growth = 1;
            @Override
            public void run() {
                if(growth>=explosionLimit){
                    this.cancel();
                }

                for (double i = 0; i < Math.PI; i += Math.PI / nbCircles) {
                    double currentRadius = Math.sin(i) * radius * growth;
                    double y = sphereCenter.getY() + Math.cos(i) * radius * growth;

                    for (double j = 0; j < Math.PI * 2; j += stepAngle) {
                        double x = sphereCenter.getX() + Math.cos(j) * currentRadius;
                        double z = sphereCenter.getZ() + Math.sin(j) * currentRadius;

                        Location particleLocation = new Location(sphereCenter.getWorld(), x, y, z);

                        mainParticle.getParticleBuilder(particleLocation).display();
                    }
                }

                growth+=growthSpeed;

            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    /***********GETTERS & SETTERS***********/

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(double growthSpeed) {
        this.growthSpeed = growthSpeed;
    }

    public int getNbCircles() {
        return nbCircles;
    }

    public void setNbCircles(int nbCircles) {
        this.nbCircles = nbCircles;
    }

    public double getExplosionLimit() {
        return explosionLimit;
    }

    public void setExplosionLimit(double explosionLimit) {
        this.explosionLimit = explosionLimit;
    }
}
