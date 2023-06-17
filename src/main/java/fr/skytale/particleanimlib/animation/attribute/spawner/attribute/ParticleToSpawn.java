package fr.skytale.particleanimlib.animation.attribute.spawner.attribute;

import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import org.bukkit.util.Vector;

import java.util.Objects;

public class ParticleToSpawn {
    private final Vector spawnLocation;
    private final int nbParticleToSpawn;
    private final APointDefinition pointDefinition;

    public ParticleToSpawn(Vector spawnLocation, int nbParticleToSpawn, APointDefinition pointDefinition) {
        if (spawnLocation == null) {
            throw new IllegalArgumentException("spawnLocation should not be null");
        }
        this.spawnLocation = spawnLocation;
        if (nbParticleToSpawn <= 0) {
            throw new IllegalArgumentException("nbParticleToSpawn should be positive");
        }
        this.nbParticleToSpawn = nbParticleToSpawn;

        if (pointDefinition == null) {
            throw new IllegalArgumentException("pointDefinition should not be null");
        }
        this.pointDefinition = pointDefinition;
    }

    public Vector getSpawnLocation() {
        return spawnLocation;
    }

    public int getNbParticleToSpawn() {
        return nbParticleToSpawn;
    }

    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleToSpawn that = (ParticleToSpawn) o;
        return nbParticleToSpawn == that.nbParticleToSpawn && Objects.equals(spawnLocation, that.spawnLocation) &&
               Objects.equals(pointDefinition, that.pointDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spawnLocation, nbParticleToSpawn, pointDefinition);
    }
}
