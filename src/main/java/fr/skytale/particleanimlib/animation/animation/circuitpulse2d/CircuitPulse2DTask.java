package fr.skytale.particleanimlib.animation.animation.circuitpulse2d;

import fr.skytale.particleanimlib.animation.attribute.AnimationPointData;
import fr.skytale.particleanimlib.animation.attribute.annotation.ForceUpdatePointsConfiguration;
import fr.skytale.particleanimlib.animation.attribute.annotation.IVariableCurrentValue;
import fr.skytale.particleanimlib.animation.attribute.area.IArea;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ForceUpdatePointsConfiguration(alwaysUpdate = true)
public class CircuitPulse2DTask extends AAnimationTask<CircuitPulse2D> {

    List<CircuitPulse2DMovingPoint> circuitPulse2DMovingPoints = new ArrayList<>();

    @IVariableCurrentValue
    private Double speed;

    @IVariableCurrentValue
    private Integer trailSize;

    @IVariableCurrentValue
    private IArea boundaryArea;

    @IVariableCurrentValue
    private Double maxAngleBetweenDirectionAndOriginDirection;

    @IVariableCurrentValue
    private ParticlesToSpawn spawner;

    @IVariableCurrentValue
    private Boolean fadeColorInTrail;

    @IVariableCurrentValue
    private Double directionChangeProbability;

    public CircuitPulse2DTask(CircuitPulse2D circuitPulse2D) {
        super(circuitPulse2D);
        startTask();
    }

    public int getNbParticleAlive() {
        return circuitPulse2DMovingPoints.size();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        updateParticles();
        removeDeadParticles();
        spawnNewParticles();
        return getParticlesToDisplay();
    }

    private void updateParticles() {
        circuitPulse2DMovingPoints.forEach(circuitPulse2DMovingPoint ->
                circuitPulse2DMovingPoint.update(boundaryArea, trailSize, directionChangeProbability, maxAngleBetweenDirectionAndOriginDirection)
        );
    }

    private void removeDeadParticles() {
        circuitPulse2DMovingPoints.removeIf(CircuitPulse2DMovingPoint::isDead);
    }

    protected void spawnNewParticles() {
        circuitPulse2DMovingPoints.addAll(
                CircuitPulse2DMovingPoint.build(spawner, speed)
        );
    }

    private List<AnimationPointData> getParticlesToDisplay() {
        return circuitPulse2DMovingPoints.stream()
                .flatMap(circuitPulse2DMovingPoint -> circuitPulse2DMovingPoint.getDisplay(fadeColorInTrail).stream())
                .collect(Collectors.toList());
    }


}
