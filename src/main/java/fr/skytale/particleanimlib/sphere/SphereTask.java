package fr.skytale.particleanimlib.sphere;

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
    int taskId;

    //Evolving variables
    double max;
    double min;
    double currentMin;
    int iterationCount;
    double step;

    public SphereTask(Sphere sphere) {
        super(sphere);
        this.nbCircles = sphere.getNbCircles();
        this.propagationType = sphere.getPropagationType();
        this.simultaneousCircles = sphere.getSimultaneousCircles();
        this.sphereType = sphere.getSphereType();
        init();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    private void init() {
        min = 0;
        max = Math.PI;
        this.iterationCount = 0;

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
        iterationCount = 0;
    }

    @Override
    public void run() {
        Location sphereCenter = animation.getBaseLocation();

        //Stop
        if (iterationCount >= ticksDuration) {
            Bukkit.getScheduler().cancelTask(taskId);
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
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


        for (double i = currentMin; propagationType == PropagationType.TOP_TO_BOTTOM ? i <= currentMax : i >= currentMax; i += step) {
            double currentRadius = Math.sin(i) * radius;
            double y = sphereCenter.getY() + Math.cos(i) * radius;

            for (double j = 0; j < Math.PI * 2; j += stepAngle) {
                double x = sphereCenter.getX() + Math.cos(j) * currentRadius;
                double z = sphereCenter.getZ() + Math.sin(j) * currentRadius;

                mainParticle.getParticleBuilder(new Location(location.getWorld(), x, y, z)).display();
            }
        }
        currentMin += step;
        iterationCount++;
    }
}
