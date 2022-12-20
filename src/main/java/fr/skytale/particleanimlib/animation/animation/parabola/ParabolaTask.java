package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParabolaTask extends AAnimationTask<Parabola> {

    private final Set<BulletData> bullets;
    @IVariableCurrentValue
    private Vector direction;
    @IVariableCurrentValue
    private Double speed;
    @IVariableCurrentValue
    private Vector gravity;
    @IVariableCurrentValue
    private Integer bulletLifetime;
    @IVariableCurrentValue
    private Integer bulletShootPeriod;

    public ParabolaTask(Parabola parabola) {
        super(parabola);
        bullets = new HashSet<>();
        startTask();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        fireNewBullets();
        return getUpdateBullets();
    }

    @Override
    protected boolean shouldUpdatePoints() {
        return bulletShootPeriod == 0 || iterationCount % bulletShootPeriod == 0 || (!bullets.isEmpty());
    }

    private void fireNewBullets() {
        if (bulletShootPeriod == 0 || iterationCount % bulletShootPeriod == 0) {
            BulletData bulletData = new BulletData();
            bulletData.location = new Vector(0, 0, 0);
            bulletData.velocity = direction.clone().normalize().multiply(speed);
            bulletData.lifetime = bulletLifetime;
            bullets.add(bulletData);
        }
    }

    private List<AnimationPointData> getUpdateBullets() {
        List<AnimationPointData> animationPoints = new ArrayList<>();
        int showPeriod = currentShowPeriod == 0 ? 1 : currentShowPeriod;
        bullets.removeIf(bulletData -> {
            if (bulletData.lifetime == 0) {
                return true;
            }
            //  For each bullet, update velocity and position for each time step using (forward) Euler's method:
            //  V(t + dt) = V(t) + dV(t + dt)/dt * dt = V(t) + gravity * dt
            //  X(t + dt) = X(t) + dX(t + dt)/dt * dt = X(t) + V(t + dt) * dt
            bulletData.velocity.add(gravity.clone().multiply(showPeriod));
            bulletData.location.add(bulletData.velocity.clone().multiply(showPeriod));
            bulletData.lifetime--;

            animationPoints.add(new AnimationPointData(bulletData.location));
            return false;
        });
        return animationPoints;
    }

    private static class BulletData {
        public Vector location;
        public Vector velocity;
        public int lifetime;
    }

}
