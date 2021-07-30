package fr.skytale.particleanimlib.animation.attribute;

import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
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

    private ParticleEffect particleEffect;
    private ParticleData particleData;
    private Color color;
    private String material;
    private int amount;
    private float speed;

    public ParticleTemplate(String particleType, Color color, String material) {
        this(particleType, color, material, 1, 0);
    }

    public ParticleTemplate(ParticleTemplate other) {
        this(other.getParticleEffect().name(), other.getColor(), other.getMaterial(), other.getAmount(), other.getSpeed());
    }

    public ParticleTemplate(String particleType, Color color, String material, int amount, float speed) {
        this.color = color;
        this.material = material;
        this.amount = amount;
        this.speed = speed;

        particleEffect = ParticleEffect.valueOf(particleType);

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
    }

    /***********GETTERS & SETTERS***********/
    public ParticleBuilder getParticleBuilder(Location location) {
        ParticleBuilder newParticleBuilder = new ParticleBuilder(particleEffect, location.clone());
        newParticleBuilder.setAmount(amount);
        newParticleBuilder.setSpeed(speed);
        newParticleBuilder.setParticleData(particleData);
        return newParticleBuilder;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
