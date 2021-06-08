package fr.skytale.particleanimlib.sphere;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Sphere extends ARoundAnimation {
    int nbCircles;

    public Sphere(){}

    @Override
    public void show(Player player) {
        Location sphereCenter;

        if(isFixedLocation())
            sphereCenter = location.clone();
        else
            sphereCenter = movingEntity.getLocation().clone().add(relativeLocation);

        for (double i = 0; i < Math.PI; i += Math.PI / nbCircles) {
            double currentRadius = Math.sin(i) * radius;
            double y = sphereCenter.getY() + Math.cos(i) * radius;

            for (double j = 0; j < Math.PI * 2; j += stepAngle) {
                double x = sphereCenter.getX() + Math.cos(j) * currentRadius;
                double z = sphereCenter.getZ() + Math.sin(j) * currentRadius;

                mainParticle.getParticleBuilder(new Location(location.getWorld(), x, y, z)).display();
            }
        }
    }

    /***********GETTERS & SETTERS***********/
    public int getNbCircles() {
        return nbCircles;
    }

    public void setNbCircles(int nbCircles) {
        this.nbCircles = nbCircles;
    }
}
