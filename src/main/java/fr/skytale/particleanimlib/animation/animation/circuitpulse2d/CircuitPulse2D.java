package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.area.IArea;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;

public class CircuitPulse2D extends AAnimation {
    private IVariable<Double> speed;
    private IVariable<Integer> trailSize;
    private IVariable<IArea> boundaryArea;
    private IVariable<Double> maxAngleBetweenDirectionAndOriginDirection;
    private IVariable<ParticlesToSpawn> spawner;
    private IVariable<Boolean> fadeColorInTrail;
    private IVariable<Double> directionChangeProbability;

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
        obj.boundaryArea = boundaryArea.copy();
        obj.maxAngleBetweenDirectionAndOriginDirection = maxAngleBetweenDirectionAndOriginDirection.copy();
        obj.spawner = spawner.copy();
        obj.fadeColorInTrail = fadeColorInTrail.copy();
        obj.directionChangeProbability = directionChangeProbability.copy();
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

    public IVariable<IArea> getBoundaryArea() {
        return boundaryArea;
    }

    public void setBoundaryArea(IVariable<IArea> boundaryArea) {
        this.boundaryArea = boundaryArea;
    }

    public IVariable<Double> getMaxAngleBetweenDirectionAndOriginDirection() {
        return maxAngleBetweenDirectionAndOriginDirection;
    }

    public void setMaxAngleBetweenDirectionAndOriginDirection(IVariable<Double> maxAngleBetweenDirectionAndOriginDirection) {
        this.maxAngleBetweenDirectionAndOriginDirection = maxAngleBetweenDirectionAndOriginDirection;
    }

    public IVariable<ParticlesToSpawn> getSpawner() {
        return spawner;
    }

    public void setSpawner(IVariable<ParticlesToSpawn> spawner) {
        this.spawner = spawner;
    }

    public IVariable<Boolean> getFadeColorInTrail() {
        return fadeColorInTrail;
    }

    public void setFadeColorInTrail(IVariable<Boolean> fadeColorInTrail) {
        this.fadeColorInTrail = fadeColorInTrail;
    }

    public IVariable<Double> getDirectionChangeProbability() {
        return directionChangeProbability;
    }

    public void setDirectionChangeProbability(IVariable<Double> directionChangeProbability) {
        this.directionChangeProbability = directionChangeProbability;
    }
}
