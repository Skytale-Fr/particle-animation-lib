package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.AnimationType;
import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public abstract class AAnimation {
    protected AnimationType animationType;
    protected Location location;
    protected IMovingEntity movingEntity;
    protected Vector relativeLocation;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;

    public abstract void show(Player player);

    public boolean isRunning() {
        //Check si l'animation tourne
        return false;
    }

    public void cancel() {
        //Stop l'action
    }

    public boolean isFixedLocation() {
        return movingEntity == null;
    }

    public Location rotateAroundAxis(Location point, Vector axis, Location pointAxis, double angle) {
        Vector v = point.clone().subtract(pointAxis).toVector(); //Vecteur de axis au point à translater
        CustomVector customVector = new CustomVector(v.getX(),v.getY(),v.getZ());
        v = customVector.rotateAroundAxis(axis, angle);
        v = v.add(pointAxis.toVector()); //Translation vers le point d'origine (La rotiation a été faite à l'origine du repère)
        return v.toLocation(point.getWorld());
    }

    /***********GETTERS & SETTERS***********/

    public AnimationType getAnimationType() {
        return animationType;
    }

    public Location getLocation() {
        return location;
    }

    public IMovingEntity getMovingEntity() {
        return movingEntity;
    }

    public Vector getRelativeLocation() {
        return relativeLocation;
    }

    public ParticleTemplate getMainParticle() {
        return mainParticle;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMovingEntity(IMovingEntity movingEntity) {
        this.movingEntity = movingEntity;
    }

    public void setRelativeLocation(Vector relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        this.mainParticle = mainParticle;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}
