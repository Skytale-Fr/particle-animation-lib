package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.spawner.SimpleSpawner;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ASpawner;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.builder.AAnimationBuilder;
import org.bukkit.util.Vector;

public class CircuitPulse2DBuilder extends AAnimationBuilder<CircuitPulse2D, CircuitPulse2DTask> {

    public CircuitPulse2DBuilder() {
        super();
        setShowPeriod(1);
        setSpeed(0.25);
        setTrailSize(3);
        setMaxSize(15);
        setSpawner(new SimpleSpawner(
                new Constant<>(new Vector(0, 0, 0)),
                new Constant<>(35),
                new Constant<>(500),
                new Constant<>(animation.getPointDefinition())
        ));
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
        checkPositive(animation.getMaxSize(), "maxSize must be positive or equal to 0", false);
        checkNotNull(animation.getSpawner(), "spawner should not be null");
        return super.getAnimation();
    }

    public void setSpeed(double speed) {
        setSpeed(new Constant<>(speed));
    }

    public void setSpeed(IVariable<Double> speed) {
        checkPositive(speed, "speed must be positive", false);
        animation.setSpeed(speed);
    }

    public void setTrailSize(int trailSize) {
        setTrailSize(new Constant<>(trailSize));
    }

    public void setTrailSize(IVariable<Integer> trailSize) {
        checkPositive(trailSize, "trailSize must be positive or equal to 0", true);
        animation.setTrailSize(trailSize);
    }

    public void setMaxSize(float maxSize) {
        setMaxSize(new Constant<>(maxSize));
    }

    public void setMaxSize(IVariable<Float> maxSize) {
        checkPositive(maxSize, "maxSize must be positive", false);
        animation.setMaxSize(maxSize);
    }

    public void setSpawner(ASpawner spawner) {
        checkNotNull(spawner, "spawner should not be null");
        animation.setSpawner(spawner);
    }
}
