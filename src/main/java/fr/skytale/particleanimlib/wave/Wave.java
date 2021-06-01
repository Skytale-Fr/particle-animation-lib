package fr.skytale.particleanimlib.wave;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Wave extends ARoundAnimation {
    private double maxRadius;
    private double step;

    public Wave() {
    }

    @Override
    public void show(Player player) {
        Location waveCenter;
        if (isFixedLocation())
            waveCenter = location.clone();
        else
            waveCenter = movingEntity.getLocation().clone().add(relativeLocation);

        new BukkitRunnable() {

            double currentRadius = radius;

            @Override
            public void run() {

                if (currentRadius >= maxRadius)
                    this.cancel();

                if (currentRadius % 2 != 0) {  //On affiche que les cercles de rayon impairs
                    double theta = 0;
                    int p = 0;
                    while (theta < 2 * Math.PI) {
                        theta = p * stepAngle;

                        double x = waveCenter.getX() + (currentRadius * Math.cos(theta));
                        double y = waveCenter.getY() + (2 * Math.exp( (-0.1 * (maxRadius - radius))/39  * currentRadius) * Math.sin(currentRadius)) + 1;
                        double z = waveCenter.getZ() + (currentRadius * Math.sin(theta));

                        Location particleLocation = new Location(waveCenter.getWorld(), x, y, z);
                        waveCenter.getWorld().spawnParticle(mainParticle.getParticleType(), particleLocation, 1, 0, 0, 0, 0, mainParticle.getParticleData());
                        p++;
                    }
                }

                currentRadius += step;

            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

    /***********GETTERS & SETTERS***********/
    public double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

}
