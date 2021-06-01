package fr.skytale.particleanimlib.atom;


import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Atom extends ARoundAnimation {
    private AAnimation sphere;
    private AAnimation circle1;
    private AAnimation circle2;
    private AAnimation circle3;
    private ParticleTemplate secondParticle;

    public Atom() {
    }

    public void displayElectron(Location nucleusCenter, Vector a, Vector b, double radiusElectrons, double theta) {

        double x = nucleusCenter.getX() + (a.getX() * radiusElectrons * Math.cos(theta)) + (b.getX() * radiusElectrons * Math.sin(theta));
        double y = nucleusCenter.getY() + (a.getY() * radiusElectrons * Math.cos(theta)) + (b.getY() * radiusElectrons * Math.sin(theta));
        double z = nucleusCenter.getZ() + (a.getZ() * radiusElectrons * Math.cos(theta)) + (b.getZ() * radiusElectrons * Math.sin(theta));

        Location particleLocation = new Location(nucleusCenter.getWorld(), x, y, z);

        nucleusCenter.getWorld().spawnParticle(secondParticle.getParticleType(), particleLocation, 1, 0, 0, 0, 0,secondParticle.getParticleData());
    }

    @Override
    public void show(Player player) {               //TODO a refaire quand le temps d'affichage et la fréquence seront implémenté

        circle1.show(player);
        circle2.show(player);
        circle3.show(player);

        new BukkitRunnable() {
            double tps = 0;

            @Override
            public void run() {

                if (tps >= 20) {
                    this.cancel();
                }
                sphere.show(player);

                tps++;
            }

        }.runTaskTimer(plugin, 0L, 2L);
    }

    /***********GETTERS & SETTERS***********/

    public ParticleTemplate getSecondParticle() {
        return secondParticle;
    }

    public void setSecondParticle(ParticleTemplate secondParticle) {
        this.secondParticle = secondParticle;
    }

    public AAnimation getSphere() {
        return sphere;
    }

    public void setSphere(AAnimation sphere) {
        this.sphere = sphere;
    }

    public AAnimation getCircle1() {
        return circle1;
    }

    public void setCircle1(AAnimation circle1) {
        this.circle1 = circle1;
    }

    public AAnimation getCircle2() {
        return circle2;
    }

    public void setCircle2(AAnimation circle2) {
        this.circle2 = circle2;
    }

    public AAnimation getCircle3() {
        return circle3;
    }

    public void setCircle3(AAnimation circle3) {
        this.circle3 = circle3;
    }
}
