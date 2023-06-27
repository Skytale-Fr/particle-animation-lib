package fr.skytale.particleanimlib.animation.attribute;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.RegularColor;
import xyz.xenondevs.particle.data.texture.BlockTexture;

import java.awt.*;

public class ParticleTemplate {

    private ParticleEffect type;
    private int amount;
    private float speed;
    private Vector offset;
    private ParticleData additionalData;

    /***********CONSTRUCTORS***********/

    /**
     * Builds a particle template
     * @param type the type of the particle
     */
    public ParticleTemplate(ParticleEffect type) {
        this(
                type,
                1,
                1,
                new Vector(0, 0, 0),
                (ParticleData) null
        );
    }

    /**
     * Builds a particle template for a particle supporting colors
     * @param type the type of the particle
     * @param color the color of the particle
     */
    public ParticleTemplate(ParticleEffect type, Color color) {
        this(
                type,
                1,
                1,
                new Vector(0, 0, 0),
                color
        );
    }

    /**
     * Builds a particle template for a particle supporting materials display
     * @param type the type of the particle
     * @param material the material of the particle
     */
    public ParticleTemplate(ParticleEffect type, Material material) {
        this(
                type,
                1,
                1,
                new Vector(0, 0, 0),
                material
        );
    }

    /**
     * Builds a particle template for a particle supporting area display and colors
     * @param type the type of the particle
     * @param amount the number of particles to display
     * @param speed the speed of the particle movement
     * @param offset the offset defining the cuboid where the particles will spawn
     * @param color the color of the particle
     */
    public ParticleTemplate(ParticleEffect type, int amount, float speed, Vector offset, Color color) {
        this(
                type,
                amount,
                speed,
                offset,
                color != null ? new RegularColor(color) : null
        );
    }

    /**
     * Builds a particle template for a particle supporting area display and materials display
     * @param type the type of the particle
     * @param amount the number of particles to display
     * @param speed the speed of the particle movement
     * @param offset the offset defining the cuboid where the particles will spawn
     * @param material the material of the particle
     */
    public ParticleTemplate(ParticleEffect type, int amount, float speed, Vector offset, Material material) {
        this(
                type,
                amount,
                speed,
                offset,
                material != null ? new BlockTexture(material) : null
        );
    }

    /**
     * Builds a particle template using ParticleData to define additional data
     * @param type the type of the particle
     * @param amount the number of particles to display
     * @param speed the speed of the particle movement
     * @param offset the offset defining the cuboid where the particles will spawn
     * @param additionalData the additional data of the particle
     */
    public ParticleTemplate(ParticleEffect type, int amount, float speed, Vector offset, ParticleData additionalData) {
        this.type = type;
        this.amount = amount;
        this.speed = speed;
        this.offset = offset;
        this.additionalData = additionalData;
    }

    /**
     * Builds a particle template using another particle template
     * @param particleTemplate the particle template to copy
     */
    public ParticleTemplate(ParticleTemplate particleTemplate) {
        this.type = particleTemplate.type;
        this.amount = particleTemplate.amount;
        this.speed = particleTemplate.speed;
        this.offset = particleTemplate.offset.clone();
        this.additionalData = particleTemplate.additionalData;
    }

    /***********GETTERS & SETTERS***********/

    public ParticleEffect getType() {
        return type;
    }

    public void setType(ParticleEffect type) {
        this.type = type;
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

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public ParticleData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(ParticleData additionalData) {
        this.additionalData = additionalData;
    }

    /***********Map to ParticleLib ParticleBuilder instance***********/

    public ParticleBuilder getParticleBuilder(Location location) {
        ParticleBuilder particleBuilder = new ParticleBuilder(type, location.clone());
        particleBuilder.setAmount(amount);
        particleBuilder.setSpeed(speed);
        particleBuilder.setOffset(offset);
        particleBuilder.setParticleData(additionalData);
        return particleBuilder;
    }
}
