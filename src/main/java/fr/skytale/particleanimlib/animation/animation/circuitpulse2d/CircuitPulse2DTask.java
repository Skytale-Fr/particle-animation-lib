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

    List<CircuitPulseMovingPoint> circuitPulseMovingPoints = new ArrayList<>();

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
        return circuitPulseMovingPoints.size();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        updateParticles();
        removeDeadParticles();
        spawnNewParticles();
        return getParticlesToDisplay();
    }

    private void updateParticles() {
        circuitPulseMovingPoints.forEach(circuitPulseMovingPoint ->
                circuitPulseMovingPoint.update(boundaryArea, trailSize, directionChangeProbability, maxAngleBetweenDirectionAndOriginDirection)
        );
    }

    private void removeDeadParticles() {
        circuitPulseMovingPoints.removeIf(CircuitPulseMovingPoint::isDead);
    }

    protected void spawnNewParticles() {
        circuitPulseMovingPoints.addAll(
                CircuitPulseMovingPoint.build(spawner, speed, animation.getPointDefinition())
        );
    }

    private List<AnimationPointData> getParticlesToDisplay() {
        return circuitPulseMovingPoints.stream()
                .flatMap(circuitPulseMovingPoint -> circuitPulseMovingPoint.getDisplay(fadeColorInTrail).stream())
                .collect(Collectors.toList());
    }


}
