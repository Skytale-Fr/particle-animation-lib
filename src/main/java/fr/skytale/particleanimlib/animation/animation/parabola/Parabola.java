package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.ARotatingAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.subanim.IDirectionSubAnimation;
import org.bukkit.util.Vector;

public class Parabola extends ARotatingAnimation implements IDirectionSubAnimation {

    private IVariable<Double> speed;
    private IVariable<Vector> gravity;
    private int bulletLifetime;
    private AnimationDirection direction;

    public Parabola() {
    }

    @Override
    public void show() {
        new ParabolaTask(this);
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

    public int getBulletLifetime() {
        return bulletLifetime;
    }

    public IVariable<Vector> getGravity() {
        return gravity;
    }

    public void setSpeed(IVariable<Double> speed) {
        this.speed = speed;
    }

    public void setGravity(IVariable<Vector> gravity) {
        this.gravity = gravity;
    }

    public void setBulletLifetime(int bulletLifetime) {
        this.bulletLifetime = bulletLifetime;
    }

    @Override
    public Parabola clone() {
        Parabola obj = (Parabola) super.clone();
        obj.speed = speed.copy();
        obj.gravity = gravity.copy();
        obj.direction = direction.clone();
        return obj;
    }
}
