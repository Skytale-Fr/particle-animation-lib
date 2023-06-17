package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class CircuitPulse2D extends AAnimation {
    IVariable<Double> speed;
    IVariable<Integer> trailSize;
    IVariable<Float> maxSize;
    IVariable<ParticlesToSpawn> spawner;

    public CircuitPulse2D() {
    }

    @Override
    public CircuitPulse2DTask show() {
        return new CircuitPulse2DTask(this);
    }

    @Override
    public CircuitPulse2D clone() {
        CircuitPulse2D obj = (CircuitPulse2D) super.clone();
        obj.speed = speed.copy();
        obj.trailSize = trailSize.copy();
        obj.maxSize = maxSize.copy();
        obj.spawner = spawner.copy();
        return obj;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public void setSpeed(IVariable<Double> speed) {
        this.speed = speed;
    }

    public IVariable<Integer> getTrailSize() {
        return trailSize;
    }

    public void setTrailSize(IVariable<Integer> trailSize) {
        this.trailSize = trailSize;
    }

    public IVariable<Float> getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(IVariable<Float> maxSize) {
        this.maxSize = maxSize;
    }

    public IVariable<ParticlesToSpawn> getSpawner() {
        return spawner;
    }

    public void setSpawner(IVariable<ParticlesToSpawn> spawner) {
        this.spawner = spawner;
    }
}
