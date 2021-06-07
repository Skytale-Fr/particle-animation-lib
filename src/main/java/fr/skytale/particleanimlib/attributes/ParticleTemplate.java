package fr.skytale.particleanimlib.attributes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.RegularColor;
import xyz.xenondevs.particle.data.texture.BlockTexture;
import xyz.xenondevs.particle.data.texture.ItemTexture;

import java.awt.*;

public class ParticleTemplate {

    private ParticleBuilder particleBuilder;
    private ParticleEffect particleEffect;
    private ParticleData particleData;
    private Color color;
    private String material;

    public ParticleTemplate(String particleType, Color color, String material) {
        this.color = color;
        this.material = material;

        particleEffect = ParticleEffect.valueOf(particleType);

        particleBuilder = new ParticleBuilder(particleEffect, null);
        particleBuilder.setAmount(1);
        particleBuilder.setSpeed(0);

        switch (particleEffect) {
            case REDSTONE:
                particleData = new RegularColor(color);
                break;

            case BLOCK_CRACK:
            case BLOCK_DUST:
            case FALLING_DUST:
                particleData = new BlockTexture(Material.valueOf(material));
                break;

            case ITEM_CRACK:
                particleData = new ItemTexture(new ItemStack(Material.valueOf(material)));
                break;
        }

        particleBuilder.setParticleData(particleData);
    }

    /***********GETTERS & SETTERS***********/
    //TODO à supprimer quand setLocation() sera implémenté dans ParticleLib
    public void setLocation(Location location) {
        particleBuilder = new ParticleBuilder(particleEffect, location);
        particleBuilder.setAmount(1);
        particleBuilder.setSpeed(0);
        particleBuilder.setParticleData(particleData);
    }

    public ParticleBuilder getParticleBuilder() {
        return particleBuilder;
    }

    public void setParticleBuilder(ParticleBuilder particleBuilder) {
        this.particleBuilder = particleBuilder;
    }

    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    public void setParticleEffect(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
    }

    public ParticleData getParticleData() {
        return particleData;
    }

    public void setParticleData(ParticleData particleData) {
        this.particleData = particleData;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
