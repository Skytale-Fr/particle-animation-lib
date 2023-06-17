package fr.skytale.particleanimlib.animation.attribute.spawner;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticleToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ASpawner;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.util.Vector;

public class SimpleSpawner extends ASpawner {

    public SimpleSpawner(
            IVariable<Vector> spawnLocation,
            IVariable<Integer> nbParticlesToSpawnPerPulse,
            IVariable<Integer> pulsePeriod,
            IVariable<APointDefinition> pointDefinition) {
        super(
                new ParticlesToSpawn(new ParticleToSpawn(
                        spawnLocation.getCurrentValue(0),
                        nbParticlesToSpawnPerPulse.getCurrentValue(0),
                        pointDefinition.getCurrentValue(0))),
                (iterationCount, previousValue1) -> {
                    if (iterationCount % pulsePeriod.getCurrentValue(iterationCount) == 0) {
                        return new ParticlesToSpawn(new ParticleToSpawn(
                                spawnLocation.getCurrentValue(iterationCount),
                                nbParticlesToSpawnPerPulse.getCurrentValue(iterationCount),
                                pointDefinition.getCurrentValue(iterationCount)
                        ));
                    }
                    return new ParticlesToSpawn();
                });
    }

}

