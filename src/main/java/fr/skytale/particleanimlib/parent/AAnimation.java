package fr.skytale.particleanimlib.parent;

import fr.skytale.particleanimlib.attributes.AnimationEndedCallback;
import fr.skytale.particleanimlib.attributes.CustomVector;
import fr.skytale.particleanimlib.attributes.ParticleTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Objects;

public abstract class AAnimation implements Cloneable {

    protected Location location;
    protected Entity movingEntity;
    protected Vector relativeLocation;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected int showFrequency;
    protected AnimationEndedCallback callback;
    protected Vector moveStepVector;
    protected Integer moveFrequency;

    public abstract void show();

    /***********GETTERS & SETTERS***********/

    public Location getLocation() {
        return location;
    }

    public Entity getMovingEntity() {
        return movingEntity;
    }

    public Vector getRelativeLocation() {
        return relativeLocation;
    }

    public ParticleTemplate getMainParticle() {
        return mainParticle;
    }

    public int getTicksDuration() {
        return ticksDuration;
    }

    public int getShowFrequency() {
        return showFrequency;
    }

    public AnimationEndedCallback getCallback() {
        return callback;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMovingEntity(Entity movingEntity) {
        this.movingEntity = movingEntity;
    }

    public void setRelativeLocation(Vector relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        this.mainParticle = mainParticle;
    }

    public void setTicksDuration(int ticksDuration) {
        this.ticksDuration = ticksDuration;
    }

    public void setShowFrequency(int showFrequency) {
        this.showFrequency = showFrequency;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setCallback(AnimationEndedCallback callback) {
        this.callback = callback;
    }

    public Vector getMoveStepVector() {
        return moveStepVector;
    }

    public void setMoveStepVector(Vector moveVector) {
        this.moveStepVector = moveVector;
    }

    public Integer getMoveFrequency() {
        return moveFrequency;
    }

    public void setMoveFrequency(Integer moveFrequency) {
        this.moveFrequency = moveFrequency;
    }

    @Override
    public Object clone() {
        AAnimation obj = null;
        try {
            obj = (AAnimation) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.location = this.location == null ? null : location.clone();
        obj.relativeLocation = this.getRelativeLocation() == null ? null : this.getRelativeLocation().clone();
        obj.mainParticle = new ParticleTemplate(this.getMainParticle());
        obj.moveStepVector = this.getMoveStepVector() == null ? null : this.getMoveStepVector();
        obj.moveFrequency = this.getMoveFrequency() == null ? null : this.getMoveFrequency();
        return obj;
    }

    public Location rotateAroundAxis(Location point, Vector axis, Location pointAxis, double angle) {
        Vector v = point.clone().subtract(pointAxis).toVector(); //Vecteur de axis au point à translater
        CustomVector customVector = new CustomVector(v.getX(), v.getY(), v.getZ());
        v = customVector.rotateAroundAxis(axis, angle);
        v = v.add(pointAxis.toVector()); //Translation vers le point d'origine (La rotiation a été faite à l'origine du repère)
        return v.toLocation(Objects.requireNonNull(point.getWorld()));
    }

}
