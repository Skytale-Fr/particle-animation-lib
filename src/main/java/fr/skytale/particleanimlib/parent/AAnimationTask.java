package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public abstract class AAnimationTask<T extends AAnimation> implements Runnable {
    protected T animation;

    protected Location location;
    protected IMovingEntity movingEntity;
    protected Vector relativeLocation;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected int showFrequency;

    public AAnimationTask(T animation) {
        this.animation = animation;
        this.location = animation.getLocation();
        this.movingEntity = animation.getMovingEntity();
        this.relativeLocation = animation.getRelativeLocation();
        this.mainParticle = animation.getMainParticle();
        this.plugin = animation.getPlugin();
        this.ticksDuration = animation.getTicksDuration();
        this.showFrequency = animation.getShowFrequency();
    }
}
