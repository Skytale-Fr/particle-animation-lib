package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import org.bukkit.util.Vector;

public class Parabola extends ARotatingAnimation implements IDirectionSubAnimation {

    private IVariable<Double> speed;
    private IVariable<Vector> gravity;
    private IVariable<Integer> bulletLifetime;
    private AnimationDirection direction;
    private IVariable<Integer> bulletShootPeriod;

    public Parabola() {
    }

    @Override
    public ParabolaTask show() {
        return new ParabolaTask(this);
    }

    @Override
    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
    public void setDirection(AnimationDirection direction) {
        this.direction = direction;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public void setSpeed(IVariable<Double> speed) {
        this.speed = speed;
    }

    public IVariable<Vector> getGravity() {
        return gravity;
    }

    public void setGravity(IVariable<Vector> gravity) {
        this.gravity = gravity;
    }

    public IVariable<Integer> getBulletLifetime() {
        return bulletLifetime;
    }

    public void setBulletLifetime(IVariable<Integer> bulletLifetime) {
        this.bulletLifetime = bulletLifetime;
    }

    public IVariable<Integer> getBulletShootPeriod() {
        return bulletShootPeriod;
    }

    public void setBulletShootPeriod(IVariable<Integer> bulletShootPeriod) {
        this.bulletShootPeriod = bulletShootPeriod;
    }

    @Override
    public Parabola clone() {
        Parabola obj = (Parabola) super.clone();
        obj.speed = speed.copy();
        obj.gravity = gravity.copy();
        obj.direction = direction.clone();
        obj.bulletShootPeriod = bulletShootPeriod.copy();
        obj.bulletLifetime = bulletLifetime.copy();
        return obj;
    }
}
