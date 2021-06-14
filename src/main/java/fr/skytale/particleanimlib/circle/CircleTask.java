package fr.skytale.particleanimlib.circle;

import fr.skytale.particleanimlib.parent.ARoundAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircleTask extends ARoundAnimationTask<Circle> {

    private Vector u;
    private Vector v;
    private Vector axis;
    private double stepAngleAlpha;
    private int taskId;

    //Evolving variables
    double alpha;
    int iterationCount;
    Location[] particleCircle = new Location[nbPoints];

    public CircleTask(Circle circle) {
        super(circle);
        this.u = circle.getU();
        this.v = circle.getV();
        this.axis = circle.getAxis();
        this.stepAngleAlpha = circle.getStepAngleAlpha();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        alpha = 0;
        iterationCount = 0;

        Location circleCenter = animation.getBaseLocation();

        //Saving position of the circle's particules
        for (int p = 0; p < nbPoints; p++) {
            double theta = p * stepAngle;

            double x = circleCenter.getX() + (u.getX() * radius * Math.cos(theta)) + (v.getX() * radius * Math.sin(theta));
            double y = circleCenter.getY() + (u.getY() * radius * Math.cos(theta)) + (v.getY() * radius * Math.sin(theta));
            double z = circleCenter.getZ() + (u.getZ() * radius * Math.cos(theta)) + (v.getZ() * radius * Math.sin(theta));

            Location particleLocation = new Location(circleCenter.getWorld(), x, y, z);

            particleCircle[p] = particleLocation;

        }
    }

    @Override
    public void run() {

        if (iterationCount >= ticksDuration) {
            Bukkit.getScheduler().cancelTask(taskId);
            if (animation.getCallback() != null) {
                animation.getCallback().run(animation);
            }
            return;
        }

        //We only show at the specified frequency
        if (showFrequency != 0 && (iterationCount % showFrequency != 0)) {
            iterationCount++;
            return;
        }


        for (int p = 0; p < nbPoints; p++) {

            if (!animation.isFixedLocation()) {
                Location currentLocation = movingEntity.getLocation();
                particleCircle[p].setX(particleCircle[p].getX() + currentLocation.getX());
                particleCircle[p].setY(particleCircle[p].getY() + currentLocation.getY());
                particleCircle[p].setZ(particleCircle[p].getZ() + currentLocation.getZ());
            }

            Location particleLocation = particleCircle[p];

            if (axis != null)
                particleLocation = animation.rotateAroundAxis(particleCircle[p], axis, location, alpha);

            mainParticle.getParticleBuilder(particleLocation).display();
        }

        alpha += stepAngleAlpha;

        iterationCount++;
    }
}
