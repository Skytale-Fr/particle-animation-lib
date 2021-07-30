package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public abstract class AAnimation implements Cloneable {

    protected APosition position;
    protected ParticleTemplate mainParticle;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected IVariable<Integer> showPeriod;
    protected AnimationEndedCallback callback;
    protected AViewers viewers;

    protected static Set<Vector> getLinePoints(Vector point1, Vector point2, double step) {
        double distance = point1.distance(point2);
        Vector stepVector = point2.clone().subtract(point1).normalize().multiply(step);
        double stepDistance = stepVector.length();
        HashSet<Vector> points = new HashSet<>();
        Vector point = point1.clone();
        for (double length = 0; length < distance; length += stepDistance) {
            points.add(point);
            point = point.clone().add(stepVector);
        }
        return points;
    }

    public abstract void show();

    /***********GETTERS & SETTERS***********/

    public APosition getPosition() {
        return position;
    }

    public void setPosition(APosition position) {
        this.position = position;
    }

    public ParticleTemplate getMainParticle() {
        return mainParticle;
    }

    public void setMainParticle(ParticleTemplate mainParticle) {
        this.mainParticle = mainParticle;
    }

    public AViewers getViewers() {
        return viewers;
    }

    public void setViewers(AViewers viewers) {
        this.viewers = viewers;
    }

    public int getTicksDuration() {
        return ticksDuration;
    }

    public void setTicksDuration(int ticksDuration) {
        this.ticksDuration = ticksDuration;
    }

    public IVariable<Integer> getShowPeriod() {
        return showPeriod;
    }

    public void setShowPeriod(IVariable<Integer> showPeriod) {
        this.showPeriod = showPeriod;
    }

    public AnimationEndedCallback getCallback() {
        return callback;
    }

    public void setCallback(AnimationEndedCallback callback) {
        this.callback = callback;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public AAnimation clone() {
        AAnimation obj = null;
        try {
            obj = (AAnimation) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.position = this.position.clone();
        obj.viewers = this.viewers.clone();
        obj.mainParticle = mainParticle == null ? null : new ParticleTemplate(this.mainParticle);
        return obj;
    }

}
