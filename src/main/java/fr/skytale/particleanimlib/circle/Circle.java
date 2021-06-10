package fr.skytale.particleanimlib.circle;


import fr.skytale.particleanimlib.parent.ARoundAnimation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Circle extends ARoundAnimation {
    private static final String STEP_ANGLE_ALPHA_0 = "If axis defined, stepAngleAlpha must not be equal to 0.";
    private Vector u;
    private Vector v;

    //Attributs propre au cercle en rotation
    private Vector axis;
    private double stepAngleAlpha;

    public Circle() {
    }

    @Override
    public void show() {
        Location circleCenter;

        if (isFixedLocation())
            circleCenter = location.clone();
        else
            circleCenter = movingEntity.getLocation().clone().add(relativeLocation);

        Location[] particleCircle = new Location[nbPoints];

        for (int p = 0; p < nbPoints; p++) {
            double theta = p * stepAngle;

            double x = circleCenter.getX() + (u.getX() * radius * Math.cos(theta)) + (v.getX() * radius * Math.sin(theta));
            double y = circleCenter.getY() + (u.getY() * radius * Math.cos(theta)) + (v.getY() * radius * Math.sin(theta));
            double z = circleCenter.getZ() + (u.getZ() * radius * Math.cos(theta)) + (v.getZ() * radius * Math.sin(theta));

            Location particleLocation = new Location(circleCenter.getWorld(), x, y, z);

            if (axis == null) {
                mainParticle.getParticleBuilder(particleLocation).display();
            } else {
                if (stepAngleAlpha == 0) {
                    throw new IllegalArgumentException(STEP_ANGLE_ALPHA_0);
                } else
                    particleCircle[p] = particleLocation;
            }
        }

        if (axis != null && stepAngleAlpha != 0) {

            //Pour chacunes de ces particules, on les pivote de l'angle alpha
            new BukkitRunnable() {

                double alpha = 0;

                @Override
                public void run() {

                    if (Math.abs(alpha) >= Math.PI * 2) {
                        this.cancel();
                    }

                    for (int p = 0; p < nbPoints; p++) {

                        if (!isFixedLocation()) {
                            Location currentLocation = movingEntity.getLocation();
                            particleCircle[p].setX(particleCircle[p].getX() + currentLocation.getX());
                            particleCircle[p].setY(particleCircle[p].getY() + currentLocation.getY());
                            particleCircle[p].setZ(particleCircle[p].getZ() + currentLocation.getZ());
                        }
                        Location particleLocation = rotateAroundAxis(particleCircle[p], axis, location, alpha);

                        mainParticle.getParticleBuilder(particleLocation).display();
                    }
                    alpha += stepAngleAlpha;
                }

            }.runTaskTimer(plugin, 0L, 1L);
        }
    }

    /***********GETTERS & SETTERS***********/

    public void setU(Vector u) {
        this.u = u;
    }

    public void setV(Vector v) {
        this.v = v;
    }

    public Vector getV() {
        return v;
    }

    public Vector getU() {
        return u;
    }

    public Vector getAxis() {
        return axis;
    }

    public void setAxis(Vector axis) {
        this.axis = axis;
    }

    public double getStepAngleAlpha() {
        return stepAngleAlpha;
    }

    public void setStepAngleAlpha(double stepAngleAlpha) {
        this.stepAngleAlpha = stepAngleAlpha;
    }
}
