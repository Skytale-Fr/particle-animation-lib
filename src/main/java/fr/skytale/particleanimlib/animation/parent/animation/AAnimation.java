package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.AnimationStopCondition;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.ParticleTemplate;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.APosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.CollisionHandler;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public abstract class AAnimation implements Cloneable {

    protected APosition position;
    protected APointDefinition pointDefinition;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected IVariable<Integer> showPeriod;
    protected Set<AnimationEndedCallback> animationEndedCallbacks = new HashSet<>();
    protected AViewers viewers;
    protected AnimationStopCondition<?> stopCondition;
    protected Set<CollisionHandler<?, AAnimationTask<?>>> collisionHandlers = new HashSet<>();
    protected IVariable<PARotation> rotation;

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

    public abstract AAnimationTask<? extends AAnimation> show();

    /***********GETTERS & SETTERS***********/

    public APosition getPosition() {
        return position;
    }

    public void setPosition(APosition position) {
        this.position = position;
    }

    public APointDefinition getPointDefinition() {
        return pointDefinition;
    }

    public void setPointDefinition(APointDefinition pointDefinition) {
        this.pointDefinition = pointDefinition;
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

    public Set<AnimationEndedCallback> getCallbacks() {
        return animationEndedCallbacks;
    }

    public void addAnimationEndedCallback(AnimationEndedCallback callback) {
        animationEndedCallbacks.add(callback);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setStopCondition(AnimationStopCondition stopCondition) { this.setStopCondition(stopCondition, false); }
    public void setStopCondition(AnimationStopCondition stopCondition, boolean infiniteTickDuration) {
        this.stopCondition = stopCondition;
        if(infiniteTickDuration) this.setTicksDuration(Integer.MAX_VALUE);
    }
    public AnimationStopCondition getStopCondition() { return this.stopCondition; }

    public void addCollisionHandler(CollisionHandler<?, AAnimationTask<?>> collisionHandler) { this.collisionHandlers.add(collisionHandler); }
    public Set<CollisionHandler<?, AAnimationTask<?>>> getCollisionHandlers() { return collisionHandlers; }

    public IVariable<PARotation> getRotation() {
        return rotation;
    }

    public void setRotation(IVariable<PARotation> rotation) {
        this.rotation = rotation;
    }

    public void setRotation(Vector u, Vector v) {
         setRotation(new Constant<>(new PARotation(u,v)));
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
        obj.pointDefinition = pointDefinition.clone();
        obj.stopCondition = this.stopCondition;
        obj.collisionHandlers = new HashSet<>(collisionHandlers);
        obj.rotation = this.rotation.copy();
        return obj;
    }

}
