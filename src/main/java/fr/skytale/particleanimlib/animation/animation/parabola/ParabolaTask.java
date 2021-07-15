package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class ParabolaTask extends ARotatingAnimationTask<Parabola> {

    public static class BulletData {
        public Vector location;
        public Vector velocity;
        public int lifetime;
    }
    private Set<BulletData> bullets;

    public ParabolaTask(Parabola parabola) {
        super(parabola);
        bullets = new HashSet<>();
        startTask();
    }

    @Override
    public void showRotated(boolean rotationChanged, Location iterationBaseLocation) {
        fireBullet(rotationChanged, iterationBaseLocation);
        updateBullets(iterationBaseLocation);
    }

    private void fireBullet(boolean rotationChanged, Location iterationBaseLocation) {
        Vector direction = animation.getDirection().getMoveVector().getCurrentValue(iterationCount);

        if (rotationChanged) {
            direction = rotation.rotateVector(direction.clone());

        }
        BulletData bulletData = new BulletData();
        bulletData.location = iterationBaseLocation.toVector();
        bulletData.velocity = direction.clone().normalize().multiply(animation.getSpeed().getCurrentValue(iterationCount));
        bulletData.lifetime = animation.getBulletLifetime();
        bullets.add(bulletData);
    }

    private void updateBullets(Location iterationBaseLocation) {
        int freq = animation.getShowFrequency().getCurrentValue(iterationCount);
        final int finalFreq = freq == 0 ? 1 : freq;
        Vector gravity = animation.getGravity().getCurrentValue(iterationCount);
        bullets.removeIf(bulletData -> {
            if (bulletData.lifetime == 0) {
                return true;
            }
//          For each bullet, update velocity and position for each time step using (forward) Euler's method:
//          V(t + dt) = V(t) + dV(t + dt)/dt * dt = V(t) + gravity * dt
//          X(t + dt) = X(t) + dX(t + dt)/dt * dt = X(t) + V(t + dt) * dt
            bulletData.velocity.add(gravity.clone().multiply(finalFreq));
            bulletData.location.add(bulletData.velocity.clone().multiply(finalFreq));
            bulletData.lifetime --;
            animation.getMainParticle().getParticleBuilder(bulletData.location.toLocation(iterationBaseLocation.getWorld())).display();
            return false;
        });
    }

}
