package fr.skytale.particleanimlib.animation.animation.parabola;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class ParabolaBuilder extends AAnimationBuilder<Parabola, ParabolaTask> {


    public static final String BULLET_LIFETIME_POSITIVE = "bulletLifetime should be strictly positive";
    public static final String BULLET_SHOOT_FREQUENCY_POSITIVE = "bulletLifetime should be strictly positive";
    public static final String GRAVITY_NOT_NULL = "gravity should not be null";
    public static final String VELOCITY_POSITIVE = "velocity must be strictly positive";
    public static final String DIRECTION_NOT_NULL = "direction should not be null";

    public ParabolaBuilder() {
        super();
        animation.setDirection(new Vector(1, 1, 0));
        animation.setSpeed((double) 1);
        animation.setBulletLifetime(60);
        animation.setBulletShootPeriod(1);
        animation.setGravity(new Vector(0, -9.81 / Math.pow(20, 2), 0));
    }

    @Override
    protected Parabola initAnimation() {
        return new Parabola();
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

    /********* Parabola specific setters ***********/

    /**
     * Set the direction of each projectile
     *
     * @param direction the direction of each projectile
     */
    public void setDirection(Vector direction) {
        animation.setDirection(new Constant<>(direction));
    }

    /**
     * Set the direction of each projectile
     *
     * @param direction the direction of each projectile
     */
    public void setDirection(IVariable<Vector> direction) {
        checkNotNull(direction, DIRECTION_NOT_NULL);
        animation.setDirection(direction);
    }

    /**
     * Set the speed of each projectile
     *
     * @param speed the speed of each projectile
     */
    public void setSpeed(IVariable<Double> speed) {
        checkPositiveAndNotNull(speed, VELOCITY_POSITIVE, false);
        animation.setSpeed(speed);
    }

    /**
     * Set the speed of each projectile
     *
     * @param speed the speed of each projectile
     */
    public void setSpeed(double speed) {
        setSpeed(new Constant<>(speed));
    }

    /**
     * Set the gravity applied to each projectile
     *
     * @param gravity the gravity applied to each projectile
     */
    public void setGravity(IVariable<Vector> gravity) {
        checkNotNull(gravity, GRAVITY_NOT_NULL);
        animation.setGravity(gravity);
    }

    /**
     * Set the gravity applied to each projectile
     *
     * @param gravity the gravity applied to each projectile
     */
    public void setGravity(Vector gravity) {
        setGravity(new Constant<>(gravity));
    }

    /**
     * Set the lifetime of each projectile in ticks
     *
     * @param bulletLifetime the lifetime of each projectile in ticks
     */
    public void setBulletLifetime(int bulletLifetime) {
        setBulletLifetime(new Constant<>(bulletLifetime));
    }

    /**
     * Set the lifetime of each projectile in ticks
     *
     * @param bulletLifetime the lifetime of each projectile in ticks
     */
    public void setBulletLifetime(IVariable<Integer> bulletLifetime) {
        checkSuperior(bulletLifetime, new Constant<>(1), BULLET_LIFETIME_POSITIVE, true);
        animation.setBulletLifetime(bulletLifetime);
    }

    /**
     * Set the period between each projectile shoot in ticks
     *
     * @param bulletShootPeriod the period between each projectile shoot in ticks
     */
    public void setBulletShootPeriod(int bulletShootPeriod) {
        animation.setBulletShootPeriod(new Constant<>(bulletShootPeriod));
    }

    /**
     * Set the period between each projectile shoot in ticks
     *
     * @param bulletShootPeriod the period between each projectile shoot in ticks
     */
    public void setBulletShootPeriod(IVariable<Integer> bulletShootPeriod) {
        checkSuperior(bulletShootPeriod, new Constant<>(1), BULLET_SHOOT_FREQUENCY_POSITIVE, true);
        animation.setBulletShootPeriod(bulletShootPeriod);
    }
}
