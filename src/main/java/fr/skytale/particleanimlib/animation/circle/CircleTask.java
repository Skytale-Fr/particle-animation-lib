package fr.skytale.particleanimlib.animation.circle;

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
    double currentRadius;

    public CircleTask(Circle circle) {
        super(circle);

        this.u = animation.getU().clone();
        this.v = animation.getV().clone();
        if(animation.getAxis()!=null) {
            this.axis = animation.getAxis().clone();
            this.stepAngleAlpha = animation.getStepAngleAlpha();
        }
        this.stepRadius = animation.getStepRadius();

        alpha = 0;
        currentRadius = radius;

        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 0).getTaskId();
    }

    @Override
    public void show(Location iterationBaseLocation) {
        if (hasDurationEnded()) {
            stopAnimation();
            return;
        }

        Location circleCenter = iterationBaseLocation;

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
        currentRadius += stepRadius;
    }
}
