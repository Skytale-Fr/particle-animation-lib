package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.parent.task.ARotatingAnimationTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ParabolaTask extends ARotatingAnimationTask<Parabola> {

    private Set<BulletData> bullets;
    private Vector direction;
    private int bulletShootPeriod;
    private int finalFreq;
    private Vector gravity;

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
        direction = animation.getDirection().getMoveVector().getCurrentValue(iterationCount);

        if (rotationChanged) {
            direction = rotation.rotateVector(direction.clone());

        }
        bulletShootPeriod = animation.getBulletShootPeriod().getCurrentValue(iterationCount);
        if (bulletShootPeriod == 0 || iterationCount % bulletShootPeriod == 0) {
            BulletData bulletData = new BulletData();
            bulletData.location = iterationBaseLocation.toVector();
            bulletData.velocity = direction.clone().normalize().multiply(animation.getSpeed().getCurrentValue(iterationCount));
            bulletData.lifetime = animation.getBulletLifetime().getCurrentValue(iterationCount);
            bullets.add(bulletData);
        }
    }

    private void updateBullets(Location iterationBaseLocation) {
        int freq = animation.getShowPeriod().getCurrentValue(iterationCount);
        finalFreq = freq == 0 ? 1 : freq;
        gravity = animation.getGravity().getCurrentValue(iterationCount);
        final Collection<? extends Player> players = animation.getViewers().getPlayers(iterationBaseLocation);
        bullets.removeIf(bulletData -> {
            if (bulletData.lifetime == 0) {
                return true;
            }
//          For each bullet, update velocity and position for each time step using (forward) Euler's method:
//          V(t + dt) = V(t) + dV(t + dt)/dt * dt = V(t) + gravity * dt
//          X(t + dt) = X(t) + dX(t + dt)/dt * dt = X(t) + V(t + dt) * dt
            bulletData.velocity.add(gravity.clone().multiply(finalFreq));
            bulletData.location.add(bulletData.velocity.clone().multiply(finalFreq));
            bulletData.lifetime--;
            animation.getMainParticle()
                    .getParticleBuilder(bulletData.location.toLocation(Objects.requireNonNull(iterationBaseLocation.getWorld())))
                    .display(players);
            return false;
        });
    }

    public static class BulletData {
        public Vector location;
        public Vector velocity;
        public int lifetime;
    }

    public Set<BulletData> getCurrentBullets() {
        return bullets;
    }

    public Vector getCurrentDirection() {
        return direction;
    }

    public int getCurrentBulletShootPeriod() {
        return bulletShootPeriod;
    }

    public int getCurrentFinalFreq() {
        return finalFreq;
    }

    public Vector getCurrentGravity() {
        return gravity;
    }

}
