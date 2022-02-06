package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.projectiledirection.AnimationDirection;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.ARotatingAnimationBuilder;
import org.bukkit.util.Vector;

public class ParabolaBuilder extends ARotatingAnimationBuilder<Parabola, ParabolaTask> {


    public static final String BULLET_LIFETIME_POSITIVE = "bulletLifetime should be strictly positive";
    public static final String BULLET_SHOOT_FREQUENCY_POSITIVE = "bulletLifetime should be strictly positive";
    public static final String GRAVITY_NOT_NULL = "gravity should not be null";
    public static final String VELOCITY_POSITIVE = "velocity must be strictly positive";
    public static final String DIRECTION_NOT_NULL = "direction should not be null";

    public ParabolaBuilder() {
        super();
        animation.setDirection(AnimationDirection.fromMoveVector(new Vector(1, 1, 1)));
        animation.setSpeed(new Constant<>((double) 1));
        animation.setBulletLifetime(new Constant<>(60));
        animation.setBulletShootPeriod(new Constant<>(1));
        animation.setGravity(new Constant<>(new Vector(0, -9.81 / Math.pow(20, 2), 0)));
    }

    @Override
    protected Parabola initAnimation() {
        return new Parabola();
    }

    /********* Parabola specific setters ***********/
    public void setDirection(AnimationDirection direction) {
        checkNotNull(direction, DIRECTION_NOT_NULL);
        animation.setDirection(direction);
    }

    public void setSpeed(IVariable<Double> speed) {
        checkPositiveAndNotNull(speed, VELOCITY_POSITIVE, false);
        animation.setSpeed(speed);
    }

    public void setSpeed(double speed) {
        setSpeed(new Constant<>(speed));
    }

    public void setGravity(IVariable<Vector> gravity) {
        checkNotNull(gravity, GRAVITY_NOT_NULL);
        animation.setGravity(gravity);
    }

    public void setGravity(Vector gravity) {
        setGravity(new Constant<>(gravity));
    }

    public void setBulletLifetime(int bulletLifetime) {
        setBulletLifetime(new Constant<>(bulletLifetime));
    }

    public void setBulletLifetime(IVariable<Integer> bulletLifetime) {
        checkSuperior(bulletLifetime, new Constant<>(1), BULLET_LIFETIME_POSITIVE, true);
        animation.setBulletLifetime(bulletLifetime);
    }

    public void setBulletShootPeriod(int bulletShootPeriod) {
        animation.setBulletShootPeriod(new Constant<>(bulletShootPeriod));
    }

    public void setBulletShootPeriod(IVariable<Integer> bulletShootPeriod) {
        checkSuperior(bulletShootPeriod, new Constant<>(1), BULLET_SHOOT_FREQUENCY_POSITIVE, true);
        animation.setBulletShootPeriod(bulletShootPeriod);
    }

    @Override
    public Parabola getAnimation() {
        checkNotNull(animation.getDirection(), DIRECTION_NOT_NULL);
        checkPositiveAndNotNull(animation.getSpeed(), VELOCITY_POSITIVE, false);
        checkNotNull(animation.getGravity(), GRAVITY_NOT_NULL);
        checkSuperior(animation.getBulletLifetime(), new Constant<>(1), BULLET_LIFETIME_POSITIVE, true);
        checkSuperior(animation.getBulletShootPeriod(), new Constant<>(1), BULLET_SHOOT_FREQUENCY_POSITIVE, true);
        return super.getAnimation();
    }
}
