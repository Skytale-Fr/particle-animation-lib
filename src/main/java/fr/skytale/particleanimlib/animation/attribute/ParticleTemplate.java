package fr.skytale.particleanimlib.animation.attribute;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ParticleTemplate extends ParticleBuilder {
    public ParticleTemplate(@NotNull Particle particle) {
        super(particle);
    }

    public ParticleTemplate(Particle particle, Color color) {
        this(particle);
        color(color);
    }

    public ParticleTemplate(Particle particle, double speed) {
        this(particle);
        speed(speed);
    }

    public ParticleTemplate(Particle particle, int count, double speed, Vector vector) {
        this(particle, speed);
        count(count);
        offset(vector.getX(), vector.getY(), vector.getZ());
    }

    public ParticleTemplate(Particle particle, int count, float speed, Vector vector, Color color) {
        this(particle, count, speed, vector);
        color(color);
    }

    public <T> ParticleTemplate(Particle particle, int count, float speed, Vector vector, T dustTransition) {
        this(particle, count, speed, vector);
        data(dustTransition);
    }

    public ParticleTemplate(ParticleTemplate particleTemplate) {
        super(particleTemplate.particle());
        this.receivers(particleTemplate.receivers());
        this.source(particleTemplate.source());
        if (particleTemplate.location() != null) {
            this.location(particleTemplate.location());
        }
        this.count(particleTemplate.count());
        this.offset(particleTemplate.offsetX(), particleTemplate.offsetY(), particleTemplate.offsetZ());
        this.extra(particleTemplate.extra());
        this.data(particleTemplate.data());
        this.force(particleTemplate.force());
    }
    public ParticleTemplate speed(double speed) {
        extra(speed);
        return this;
    }
}
