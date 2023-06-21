package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.area.IArea;
import fr.skytale.particleanimlib.animation.attribute.area.Polygon2D;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.ParticlePointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.SimpleSpawner;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ISpawner;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class CircuitPulse2DBuilder extends AAnimationBuilder<CircuitPulse2D, CircuitPulse2DTask> {

    public CircuitPulse2DBuilder() {
        super();
        setShowPeriod(1);
        setSpeed(0.25);
        setTrailSize(4);
        setBoundaryArea(new Polygon2D(RANDOM.nextInt(2)+3, 8));
        setMaxAngleBetweenDirectionAndOriginDirection(Math.PI / 4);
        setSpawner(new SimpleSpawner(
                new Constant<>(new Vector(0, 0, 0)),
                new Constant<>(40),
                new Constant<>(15),
                new Constant<>(new ParticlePointDefinition(new ParticleTemplate(
                        ParticleEffect.REDSTONE,
                        new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256))
                )))
        ));
        setFadeColorInTrail(true);
        setDirectionChangeProbability(0.1);
        setTicksDuration(500);
        setStopCondition(animationTask -> animationTask.getNbParticleAlive() == 0, false);
    }

    @Override
    protected CircuitPulse2D initAnimation() {
        return new CircuitPulse2D();
    }

    @Override
    public CircuitPulse2D getAnimation() {
        checkPositive(animation.getSpeed(), "speed must be positive", false);
        checkPositive(animation.getTrailSize(), "trailSize must be positive or equal to 0", true);
        checkNotNull(animation.getBoundaryArea(), "boundaryArea should not be null");
        checkNotNull(animation.getSpawner(), "spawner should not be null");
        checkNotNull(animation.getFadeColorInTrail(), "fadeColorInTrail should not be null");
        checkNotNullAndBetween(animation.getDirectionChangeProbability(), 0, 1, "directionChangeProbability must be between 0 and 1");
        checkPositive(animation.getMaxAngleBetweenDirectionAndOriginDirection(), "maxAngleBetweenDirectionAndOriginDirection must be positive or equal to 0", true);
        return super.getAnimation();
    }

    /**
     * Defines the speed of each particle in block/tick
     *
     * @param speed the speed of the particles in block/tick
     */
    public void setSpeed(double speed) {
        setSpeed(new Constant<>(speed));
    }


    /**
     * Defines the speed of each particle in block/tick
     *
     * @param speed the speed of the particles in block/tick
     */
    public void setSpeed(IVariable<Double> speed) {
        checkPositive(speed, "speed must be positive", false);
        animation.setSpeed(speed);
    }

    /**
     * Defines the size of the trail of each particle
     *
     * @param trailSize the size of the trail of each particle
     */
    public void setTrailSize(int trailSize) {
        setTrailSize(new Constant<>(trailSize));
    }

    /**
     * Defines the size of the trail of each particle
     *
     * @param trailSize the size of the trail of each particle
     */
    public void setTrailSize(IVariable<Integer> trailSize) {
        checkPositive(trailSize, "trailSize must be positive or equal to 0", true);
        animation.setTrailSize(trailSize);
    }

    /**
     * Defines the area the particles will be confined in
     *
     * @param boundaryArea the area the particles will be confined in
     */
    public void setBoundaryArea(IArea boundaryArea) {
        setBoundaryArea(new Constant<>(boundaryArea));
    }

    /**
     * Defines the area the particles will be confined in
     *
     * @param boundaryArea the area the particles will be confined in
     */
    public void setBoundaryArea(IVariable<IArea> boundaryArea) {
        checkNotNull(boundaryArea, "boundaryArea should not be null");
        animation.setBoundaryArea(boundaryArea);
    }

    /**
     * Defines the minimum angle between the direction of a particle and its origin
     *
     * @param maxAngleBetweenDirectionAndOriginDirection the minimum angle between the direction of a particle and its origin
     */
    public void setMaxAngleBetweenDirectionAndOriginDirection(double maxAngleBetweenDirectionAndOriginDirection) {
        setMaxAngleBetweenDirectionAndOriginDirection(new Constant<>(maxAngleBetweenDirectionAndOriginDirection));
    }

    /**
     * Defines the minimum angle between the direction of a particle and its origin
     *
     * @param maxAngleBetweenDirectionAndOriginDirection the minimum angle between the direction of a particle and its origin
     */
    public void setMaxAngleBetweenDirectionAndOriginDirection(IVariable<Double> maxAngleBetweenDirectionAndOriginDirection) {
        checkPositive(maxAngleBetweenDirectionAndOriginDirection, "maxAngleBetweenDirectionAndOriginDirection must be positive or equal to 0", true);
        animation.setMaxAngleBetweenDirectionAndOriginDirection(maxAngleBetweenDirectionAndOriginDirection);
    }

    /**
     * Defines the spawner of the particles
     *
     * @param spawner how the particles will spawn over time
     */
    public void setSpawner(ISpawner spawner) {
        checkNotNull(spawner, "spawner should not be null");
        animation.setSpawner(spawner);
    }

    /**
     * Fades the color of the trail particles
     *
     * @param fadeColorInTrail if true, fade the color of the trail particles
     */
    public void setFadeColorInTrail(boolean fadeColorInTrail) {
        setFadeColorInTrail(new Constant<>(fadeColorInTrail));
    }

    /**
     * Fades the color of the trail particles
     *
     * @param fadeColorInTrail if true, fade the color of the trail particles
     */
    public void setFadeColorInTrail(IVariable<Boolean> fadeColorInTrail) {
        animation.setFadeColorInTrail(fadeColorInTrail);
        checkNotNull(fadeColorInTrail, "fadeColorInTrail should not be null");
    }

    /**
     * Defines the probability of a particle to change direction
     *
     * @param directionChangeProbability the probability of a particle to change direction
     */
    public void setDirectionChangeProbability(double directionChangeProbability) {
        setDirectionChangeProbability(new Constant<>(directionChangeProbability));
    }

    /**
     * Defines the probability of a particle to change direction
     *
     * @param directionChangeProbability the probability of a particle to change direction
     */
    public void setDirectionChangeProbability(IVariable<Double> directionChangeProbability) {
        animation.setDirectionChangeProbability(directionChangeProbability);
        checkNotNullAndBetween(directionChangeProbability, 0, 1, "directionChangeProbability must be between 0 and 1");
    }
}
