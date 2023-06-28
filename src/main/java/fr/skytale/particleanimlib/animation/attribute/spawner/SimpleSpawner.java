package fr.skytale.particleanimlib.animation.attribute.spawner;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticleToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ISpawner;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.util.Vector;

/**
 * A simple spawner that spawn a given number of particles at a given location every given period.
 */
public class SimpleSpawner extends CallbackVariable<ParticlesToSpawn> implements ISpawner {


    /**
     * Create a simple spawner that spawn a given number of particles at a given location every given period.
     * @param spawnLocation the location (relative to animation position) where the particles will be spawned
     * @param nbParticlesToSpawnPerPulse the number of particles to spawn per pulse
     * @param pulsePeriod the period between two pulses
     * @param pointDefinition the point definition of the points to show (usually a ParticleTemplate)
     */
    public SimpleSpawner(
            Vector spawnLocation,
            int nbParticlesToSpawnPerPulse,
            int pulsePeriod,
            APointDefinition pointDefinition
    ) {
        this(
                new Constant<>(spawnLocation),
                new Constant<>(nbParticlesToSpawnPerPulse),
                new Constant<>(pulsePeriod),
                new Constant<>(pointDefinition)
        );
    }

    /**
     * Create a simple spawner that spawn a given number of particles at a given location every given period.
     * @param spawnLocation the location (relative to animation position) where the particles will be spawned
     * @param nbParticlesToSpawnPerPulse the number of particles to spawn per pulse
     * @param pulsePeriod the period between two pulses
     * @param pointDefinition the point definition of the points to show (usually a ParticleTemplate)
     */
    public SimpleSpawner(
            IVariable<Vector> spawnLocation,
            IVariable<Integer> nbParticlesToSpawnPerPulse,
            IVariable<Integer> pulsePeriod,
            IVariable<APointDefinition> pointDefinition) {
        super(
                iterationCount -> {
                    final ParticlesToSpawn particlesToSpawns = new ParticlesToSpawn();
                    if (iterationCount % pulsePeriod.getCurrentValue(iterationCount) == 0) {
                        particlesToSpawns.add(new ParticleToSpawn(
                                spawnLocation.getCurrentValue(iterationCount),
                                nbParticlesToSpawnPerPulse.getCurrentValue(iterationCount),
                                pointDefinition.getCurrentValue(iterationCount)
                        ));
                    }
                    return particlesToSpawns;
                });
    }

}

