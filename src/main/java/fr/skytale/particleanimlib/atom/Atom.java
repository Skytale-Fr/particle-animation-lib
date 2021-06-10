package fr.skytale.particleanimlib.atom;


import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Atom extends ARoundAnimation {
    private AAnimation sphere;
    private AAnimation circle1;
    private AAnimation circle2;
    private AAnimation circle3;
    private ParticleTemplate secondParticle;

    public Atom() {
    }

    @Override
    public void show() {               //TODO a refaire quand le temps d'affichage et la fréquence seront implémenté

        circle1.show();
        circle2.show();
        circle3.show();

        new BukkitRunnable() {
            double tps = 0;

            @Override
            public void run() {

                if (tps >= 20) {
                    this.cancel();
                }
                sphere.show();

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
