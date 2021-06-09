package fr.skytale.particleanimlib.spiral;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Spiral extends ARoundAnimation {
    private double step;
    private Location target;
    private  double growthSpeed;

    public Spiral() {
    }

    @Override
    public void show(Player player) {
        double distance = target.distance(location);

        Vector start = location.toVector();
        Vector end = target.toVector();
        Vector stepVector = end.clone().subtract(start).normalize().multiply(step); //Le step mais version vector

        Vector directorVector = end.clone().subtract(start);    //vecteur directeur de l'axe central

        new BukkitRunnable() {
            double theta = 0;
            double length = 0;
            double radiusCopy = radius;

            @Override
            public void run() {

                if (length >= distance) {
                    this.cancel();
                }

                //Axe central
                Location particleLocation = new Location(location.getWorld(), start.getX(), start.getY(), start.getZ());
                length += step;

                //Les deux spirales
                /*Soit directorVector=(a,b,c)
                Alors l'équation du plan contenant le point (0,0,0) et admettant directorVector pour vecteur normal est ax + by + cz = 0
                On cherche le vecteur radiusVector appartenant au plan*/

                //Calcul du vecteur radiusVector
                Vector radiusVector;
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

                radiusVector.normalize().multiply(radiusCopy);      //radiusVector à pour norme le rayon

                //Affichage des particules
                Location particleSpiral1Location = particleLocation.clone().add(radiusVector);

                particleSpiral1Location = rotateAroundAxis(particleSpiral1Location, directorVector, particleLocation, theta);

                mainParticle.getParticleBuilder(particleSpiral1Location).display();

                theta += stepAngle;
                start.add(stepVector);
                radiusCopy+=growthSpeed;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
    /***********GETTERS & SETTERS***********/

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getGrowthSpeed() {
        return growthSpeed;
    }

    public void setGrowthSpeed(double growthSpeed) {
        this.growthSpeed = growthSpeed;
    }
}
