package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import org.bukkit.util.Vector;

public class Parabola extends AAnimation {

    private IVariable<Vector> direction;
    private IVariable<Double> speed;
    private IVariable<Vector> gravity;
    private IVariable<Integer> bulletLifetime;
    private IVariable<Integer> bulletShootPeriod;

    public Parabola() {
    }

    @Override
    public ParabolaTask show() {
        return new ParabolaTask(this);
    }

    @Override
    public Parabola clone() {
        Parabola obj = (Parabola) super.clone();
        obj.speed = speed.copy();
        obj.gravity = gravity.copy();
        obj.direction = direction.copy();
        obj.bulletShootPeriod = bulletShootPeriod.copy();
        obj.bulletLifetime = bulletLifetime.copy();
        return obj;
    }

    public IVariable<Vector> getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        setDirection(new Constant<>(direction));
    }

    public void setDirection(IVariable<Vector> direction) {
        this.direction = direction;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        setSpeed(new Constant<>(speed));
    }

    public void setSpeed(IVariable<Double> speed) {
        this.speed = speed;
    }

    public IVariable<Vector> getGravity() {
        return gravity;
    }

    public void setGravity(Vector gravity) {
        setGravity(new Constant<>(gravity));
    }

    public void setGravity(IVariable<Vector> gravity) {
        this.gravity = gravity;
    }

    public IVariable<Integer> getBulletLifetime() {
        return bulletLifetime;
    }

    public void setBulletLifetime(Integer bulletLifetime) {
        setBulletLifetime(new Constant<>(bulletLifetime));
    }

    public void setBulletLifetime(IVariable<Integer> bulletLifetime) {
        this.bulletLifetime = bulletLifetime;
    }

    public IVariable<Integer> getBulletShootPeriod() {
        return bulletShootPeriod;
    }

    public void setBulletShootPeriod(Integer bulletShootPeriod) {
        setBulletShootPeriod(new Constant<>(bulletShootPeriod));
    }

    public void setBulletShootPeriod(IVariable<Integer> bulletShootPeriod) {
        this.bulletShootPeriod = bulletShootPeriod;
    }
}
