package fr.skytale.particleanimlib.animation.attribute.spawner.attribute;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class ParticlesToSpawn extends HashSet<ParticleToSpawn> {
    public ParticlesToSpawn() {
        super();
    }

    public ParticlesToSpawn(ParticleToSpawn... particleToSpawnArray) {
        super(Arrays.asList(particleToSpawnArray));
    }

    public ParticlesToSpawn(@NotNull Collection<? extends ParticleToSpawn> c) {
        super(c);
    }
}
