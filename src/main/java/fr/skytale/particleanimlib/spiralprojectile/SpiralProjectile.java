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
    public void show(Player player) {
        double distance = target.distance(location);

        Vector start = location.toVector();
        Vector end = target.toVector();
        Vector stepVector = end.clone().subtract(start).normalize().multiply(step); //Le step mais version vector

        //Vector directorVector = end.clone().subtract(start);    //vecteur directeur de l'axe central
        spiral1.show(player);
        spiral2.show(player);

        new BukkitRunnable() {
            double length = 0;

            @Override
            public void run() {

                if (length >= distance) {
                    this.cancel();
                }

                //Axe central
                Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
                location.getWorld().spawnParticle(mainParticle.getParticleType(), particleLocation, 0, 0, 0, 0, 0, mainParticle.getParticleData());
                length += step;

                //Les deux spirales
                /*Soit directorVector=(a,b,c)
                Alors l'équation du plan contenant le point (0,0,0) et admettant directorVector pour vecteur normal est ax + by + cz = 0
                On cherche le vecteur radiusVector appartenant au plan*/

                //Calcul du vecteur radiusVector
                /*Vector radiusVector;
                double a = directorVector.getX();
                double b = directorVector.getY();
                double c = directorVector.getZ();


                if (a == 0) {
                    if (b == 0)
                        radiusVector = new Vector(1, 1, 0);
                    else if (c == 0)
                        radiusVector = new Vector(1, 0, 1);
                    else
                        radiusVector = new Vector(1, 1, -b / c);
                } else if (b == 0) {
                    if (c == 0)
                        radiusVector = new Vector(0, 1, 1);
                    else
                        radiusVector = new Vector(1, 1, -a / c);
                } else if (c == 0)
                    radiusVector = new Vector(1, -b / a, 1);
                else
                    radiusVector = new Vector(1, 1, (-a - b) / c);

                radiusVector.normalize().multiply(radius);      //radiusVector à pour norme le rayon*/


                //Affichage des particules
                /*Location particleSpiral1Location = particleLocation.clone().add(radiusVector);
                Location particleSpiral2Location = particleLocation.clone().subtract(radiusVector);

                particleSpiral1Location = rotateAroundAxis(particleSpiral1Location, directorVector, particleLocation, theta);
                particleSpiral2Location = rotateAroundAxis(particleSpiral2Location, directorVector, particleLocation, theta);

                location.getWorld().spawnParticle(particleSpiral1.getParticleType(), particleSpiral1Location, 0, 0, 0, 0, 0, particleSpiral1.getParticleData());
                location.getWorld().spawnParticle(particleSpiral2.getParticleType(), particleSpiral2Location, 0, 0, 0, 0, 0, particleSpiral2.getParticleData());*/

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
