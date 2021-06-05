package fr.skytale.particleanimlib.attributes;

import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.data.ParticleData;

public class ParticleTemplate {
    private Particle particleType;
    private ParticleBuilder particleBuilder;
    private ParticleData particleData;
    /*private Particle.DustOptions particleOptions;
    private ItemStack particleItemStack;
    private BlockData particleBlockData;*/

    public ParticleTemplate(Particle particleType) {
        this.particleType = particleType;
    }

    /***********GETTERS & SETTERS***********/

    public void setParticleType(Particle particleType) {
        this.particleType = particleType;
    }

    public Particle getParticleType() {
        return particleType;
    }

    public ParticleData getParticleData() {
        return particleData;
    }

    public void setParticleData(ParticleData particleData) {
        this.particleData = particleData;
    }

    public ParticleBuilder getParticleBuilder() {
        return particleBuilder;
    }

    public void setParticleBuilder(ParticleBuilder particleBuilder) {
        this.particleBuilder = particleBuilder;
    }
}
