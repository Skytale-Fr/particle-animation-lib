package fr.skytale.particleanimlib.animation.attribute.spawner;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticleToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.attribute.ParticlesToSpawn;
import fr.skytale.particleanimlib.animation.attribute.spawner.parent.ISpawner;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.util.Vector;

public class SimpleSpawner extends CallbackVariable<ParticlesToSpawn> implements ISpawner {

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

