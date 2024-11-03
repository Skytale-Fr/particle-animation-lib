package fr.skytale.particleanimlib.animation.animation.circuitpulse3d;

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
public class CircuitPulse3DTask extends AAnimationTask<CircuitPulse3D> {

    List<CircuitPulse3DMovingPoint> circuitPulse3DMovingPoints = new ArrayList<>();

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

    public CircuitPulse3DTask(CircuitPulse3D circuitPulse3D) {
        super(circuitPulse3D);
        startTask();
    }

    public int getNbParticleAlive() {
        return circuitPulse3DMovingPoints.size();
    }

    @Override
    protected List<AnimationPointData> computeAnimationPoints() {
        updateParticles();
        removeDeadParticles();
        spawnNewParticles();
        return getParticlesToDisplay();
    }

    private void updateParticles() {
        circuitPulse3DMovingPoints.forEach(circuitPulse3DMovingPoint ->
                circuitPulse3DMovingPoint.update(boundaryArea, trailSize, directionChangeProbability, maxAngleBetweenDirectionAndOriginDirection)
        );
    }

    private void removeDeadParticles() {
        circuitPulse3DMovingPoints.removeIf(CircuitPulse3DMovingPoint::isDead);
    }

    protected void spawnNewParticles() {
        circuitPulse3DMovingPoints.addAll(
                CircuitPulse3DMovingPoint.build(spawner, speed)
        );
    }

    private List<AnimationPointData> getParticlesToDisplay() {
        return circuitPulse3DMovingPoints.stream()
                .flatMap(circuitPulse3DMovingPoint -> circuitPulse3DMovingPoint.getDisplay(fadeColorInTrail).stream())
                .collect(Collectors.toList());
    }


}
