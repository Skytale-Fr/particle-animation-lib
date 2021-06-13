package fr.skytale.particleanimlib.spiralprojectile;


import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpiralProjectile extends ARoundAnimation {
    private Location target;
    private AAnimation spiral1;
    private AAnimation spiral2;
    private double step;

    public SpiralProjectile() {
    }

    @Override
    public void show() {
        double distance = target.distance(location);

        Vector start = location.toVector();
        Vector end = target.toVector();
        Vector stepVector = end.clone().subtract(start).normalize().multiply(step); //Le step mais version vector

        spiral1.show();
        spiral2.show();

        final AAnimation finalThis = this;

        new BukkitRunnable() {
            double length = 0;

            @Override
            public void run() {

                if (length >= distance) {
                    this.cancel();
                    if (getCallback() != null) {
                        getCallback().run(finalThis);
                    }
                }

                //Axe central
                Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
                mainParticle.getParticleBuilder(particleLocation).display();
                length += step;

                start.add(stepVector);
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    /***********GETTERS & SETTERS***********/
    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public AAnimation getSpiral1() {
        return spiral1;
    }

    public void setSpiral1(AAnimation spiral1) {
        this.spiral1 = spiral1;
    }

    public AAnimation getSpiral2() {
        return spiral2;
    }

    public void setSpiral2(AAnimation spiral2) {
        this.spiral2 = spiral2;
    }
}
