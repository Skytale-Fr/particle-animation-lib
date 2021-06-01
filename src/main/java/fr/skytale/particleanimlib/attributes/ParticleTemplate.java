package fr.skytale.particleanimlib.attributes;

import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class ParticleTemplate {
    private static final String NOT_IMPLEMENTED = "Error : particle not implemented";
    private Particle particleType;
    private Particle.DustOptions particleOptions;
    private ItemStack particleItemStack;
    private BlockData particleBlockData;

    public ParticleTemplate(Particle particleType, Particle.DustOptions particleOptions, ItemStack particleItemStack, BlockData particleBlockData) {
        this.particleType = particleType;
        this.particleOptions = particleOptions;
        this.particleItemStack = particleItemStack;
        this.particleBlockData = particleBlockData;
    }

    /***********GETTERS & SETTERS***********/

    public void setParticleType(Particle particleType) {
        this.particleType = particleType;
    }

    public void setParticleOptions(Particle.DustOptions particleOptions) {
        this.particleOptions = particleOptions;
    }

    public void setParticleItemStack(ItemStack particleItemStack) {
        this.particleItemStack = particleItemStack;
    }

    public void setParticleBlockData(BlockData particleBlockData) {
        this.particleBlockData = particleBlockData;
    }

    public Particle getParticleType() {
        return particleType;
    }

    public Object getParticleData(){
        Object data=null;

        switch(particleType){
            case REDSTONE:
                 data = particleOptions;
                break;

            case ITEM_CRACK:
                data = particleItemStack;
                break;

            case BLOCK_CRACK:
            case BLOCK_DUST:
            case FALLING_DUST:
                data = particleBlockData;
                break;

            case LEGACY_BLOCK_CRACK:
            case LEGACY_BLOCK_DUST:
            case LEGACY_FALLING_DUST:
                System.out.println(NOT_IMPLEMENTED);
                break;
        }

        return data;
    }
}
