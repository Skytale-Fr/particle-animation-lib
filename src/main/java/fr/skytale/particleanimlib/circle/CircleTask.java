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
    private double stepRadius;
    private int taskId;

    //Evolving variables
    double alpha;
    int iterationCount;
    Location[] particleCircle = new Location[nbPoints];
    double currentRadius;

    public CircleTask(Circle circle) {
        super(circle);
        this.u = circle.getU();
        this.v = circle.getV();
        this.axis = circle.getAxis();
        this.stepAngleAlpha = circle.getStepAngleAlpha();
        this.stepRadius = circle.getStepRadius();
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();

        init();
    }

    private void init() {
        alpha = 0;
        iterationCount = 0;
        currentRadius = radius;
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

        Location circleCenter = animation.getBaseLocation();

        for (int p = 0; p < nbPoints; p++) {
            double theta = p * stepAngle;

            double x = circleCenter.getX() + (u.getX() * currentRadius * Math.cos(theta)) + (v.getX() * currentRadius * Math.sin(theta));
            double y = circleCenter.getY() + (u.getY() * currentRadius * Math.cos(theta)) + (v.getY() * currentRadius * Math.sin(theta));
            double z = circleCenter.getZ() + (u.getZ() * currentRadius * Math.cos(theta)) + (v.getZ() * currentRadius * Math.sin(theta));

            Location particleLocation = new Location(circleCenter.getWorld(), x, y, z);

            if (axis != null)
                particleLocation = animation.rotateAroundAxis(particleLocation, axis, location, alpha);

            mainParticle.getParticleBuilder(particleLocation).display();
        }

        alpha += stepAngleAlpha;
        iterationCount++;
        currentRadius+=stepRadius;
    }
}
