package fr.skytale.particleanimlib.animation.animation.circle;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircleTask extends AAnimationTask<Circle> {

    public CircleTask(Circle circle) {
        super(circle);

        startTask();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        Vector u = animation.getU().getCurrentValue(iterationCount).clone().normalize();
        Vector v = animation.getV().getCurrentValue(iterationCount).clone().normalize();

        double stepAngle = animation.getAngleBetweenEachPoint().getCurrentValue(iterationCount);
        double radius = animation.getRadius().getCurrentValue(iterationCount);
        int nbPoints = animation.getNbPoints().getCurrentValue(iterationCount);

        Vector rotationAxis = null;
        Double rotationAngleAlpha = null;

        if (animation.getRotationAxis() != null) {
            rotationAxis = animation.getRotationAxis().getCurrentValue(iterationCount);
            rotationAngleAlpha = animation.getRotationAngleAlpha().getCurrentValue(iterationCount);
        }

        ParticleTemplate particleTemplate = animation.getMainParticle();

        for (int pointIndex = 0; pointIndex < nbPoints; pointIndex ++) {
            double theta = pointIndex * stepAngle;

            double x = iterationBaseLocation.getX() + (u.getX() * radius * Math.cos(theta)) + (v.getX() * radius * Math.sin(theta));
            double y = iterationBaseLocation.getY() + (u.getY() * radius * Math.cos(theta)) + (v.getY() * radius * Math.sin(theta));
            double z = iterationBaseLocation.getZ() + (u.getZ() * radius * Math.cos(theta)) + (v.getZ() * radius * Math.sin(theta));

            Location particleLocation = new Location(iterationBaseLocation.getWorld(), x, y, z);

            if (rotationAxis != null)
                particleLocation = rotateAroundAxis(particleLocation, rotationAxis, iterationBaseLocation, rotationAngleAlpha);

            particleTemplate.getParticleBuilder(particleLocation).display();
        }
    }
}
