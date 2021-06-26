package fr.skytale.particleanimlib.animation.sphere;

import fr.skytale.particleanimlib.attributes.PropagationType;
import fr.skytale.particleanimlib.attributes.SphereType;
import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SphereTask extends ARoundAnimationTask<Sphere> {
    int nbCircles;
    PropagationType propagationType;
    int simultaneousCircles;
    SphereType sphereType;

    //Evolving variables
    double max;
    double min;
    double currentMin;
    double step;

    public SphereTask(Sphere sphere) {
        super(sphere);

        this.nbCircles = animation.getNbCircles();
        this.propagationType = animation.getPropagationType();
        this.simultaneousCircles = animation.getSimultaneousCircles();
        this.sphereType = animation.getSphereType();
        min = 0;
        max = Math.PI;

        switch (sphereType) {
            case HALF_TOP:
                max = Math.PI / 2;
                break;
            case HALF_BOTTOM:
                min = Math.PI / 2;
                break;
        }

        //Si on va de bas en haut, le min et le max est inversé
        if (propagationType == PropagationType.BOTTOM_TO_TOP) {
            double tmp = min;
            min = max;
            max = tmp;
        }

        step = (max - min) / nbCircles;

        currentMin = min;

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        Location sphereCenter = iterationBaseLocation;

        //Stop
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        //Define the vertical limits of the sphere that will be shown
        double currentMax;

        if (propagationType != null) {
            currentMax = currentMin + step * simultaneousCircles;
            if (propagationType == PropagationType.BOTTOM_TO_TOP && currentMax < max) {
                currentMax = max;
            } else if (propagationType == PropagationType.TOP_TO_BOTTOM && currentMax > max) {
                currentMax = max;
            }
        } else {
            currentMax = max;
        }


        for (double i = currentMin; propagationType == PropagationType.BOTTOM_TO_TOP ? i >= currentMax : i <= currentMax; i += step) {
            double currentRadius = Math.sin(i) * radius;
            double y = sphereCenter.getY() + Math.cos(i) * radius;

            for (double j = 0; j < Math.PI * 2; j += stepAngle) {
                double x = sphereCenter.getX() + Math.cos(j) * currentRadius;
                double z = sphereCenter.getZ() + Math.sin(j) * currentRadius;

                mainParticle.getParticleBuilder(new Location(location.getWorld(), x, y, z)).display();
            }
        }
        currentMin += step;
    }
}
