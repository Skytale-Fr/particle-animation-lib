package fr.skytale.particleanimlib.animation.sphere;

import fr.skytale.particleanimlib.animation.attributes.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SphereTask extends AAnimationTask<Sphere> {

    //Evolving variables
    private final double max;
    private final double min;

    private Double currentMin;

    public SphereTask(Sphere sphere) {
        super(sphere);

        double tmpMin = 0, tmpMax = Math.PI;

        switch (animation.getSphereType()) {
            case HALF_TOP:
                tmpMax = Math.PI / 2;
                break;
            case HALF_BOTTOM:
                tmpMin = Math.PI / 2;
                break;
        }

        //Si on va de bas en haut, le min et le max est inversé
        if (animation.getPropagationType() == Sphere.PropagationType.TOP_TO_BOTTOM) {
            min = tmpMin;
            max = tmpMax;
        } else {
            min = tmpMax;
            max = tmpMin;
        }

        currentMin = min;

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {

        //Stop
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        Sphere.PropagationType propagationType = animation.getPropagationType();
        double step = (max - min) / animation.getNbCircles().getCurrentValue(iterationCount);

        //Define the vertical limits of the sphere that will be shown
        double currentMax;

        if (propagationType != null) {
            Integer simultaneousCircles = animation.getSimultaneousCircles().getCurrentValue(iterationCount);
            currentMax = currentMin + step * simultaneousCircles;
            if (propagationType == Sphere.PropagationType.BOTTOM_TO_TOP && currentMax < max) {
                currentMax = max;
            } else if (propagationType == Sphere.PropagationType.TOP_TO_BOTTOM && currentMax > max) {
                currentMax = max;
            }
        } else {
            currentMax = max;
        }

        double radius = animation.getRadius().getCurrentValue(iterationCount);
        double angleBetweenEachPoint = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        ParticleTemplate particleTemplate = animation.getMainParticle();

        for (double i = currentMin; propagationType == Sphere.PropagationType.BOTTOM_TO_TOP ? i >= currentMax : i <= currentMax; i += step) {
            double currentRadius = Math.sin(i) * radius;
            double y = iterationBaseLocation.getY() + Math.cos(i) * radius;

            for (double j = 0; j < Math.PI * 2; j += angleBetweenEachPoint) {
                double x = iterationBaseLocation.getX() + Math.cos(j) * currentRadius;
                double z = iterationBaseLocation.getZ() + Math.sin(j) * currentRadius;

                particleTemplate.getParticleBuilder(new Location(iterationBaseLocation.getWorld(), x, y, z)).display();
            }
        }
        currentMin += step;
    }
}
