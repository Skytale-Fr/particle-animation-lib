package fr.skytale.particleanimlib.animation.parent.animation;

import fr.skytale.particleanimlib.animation.attribute.AnimationEndedCallback;
import fr.skytale.particleanimlib.animation.attribute.AnimationStopCondition;
import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.attribute.viewers.AViewers;
import fr.skytale.particleanimlib.animation.collision.handler.CollisionHandler;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public abstract class AAnimation implements Cloneable {

    protected IPosition position;
    protected APointDefinition pointDefinition;
    protected JavaPlugin plugin;
    protected int ticksDuration;
    protected IVariable<Integer> showPeriod;
    protected Set<AnimationEndedCallback> animationEndedCallbacks = new HashSet<>();
    protected AViewers viewers;
    protected AnimationStopCondition<?> stopCondition;
    protected Set<CollisionHandler<?, AAnimationTask<?>>> collisionHandlers = new HashSet<>();
    protected IVariable<PARotation> rotation;

    public abstract AAnimationTask<? extends AAnimation> show();

    /***********GETTERS & SETTERS***********/

    public IPosition getPosition() {
        return position;
    }

    public void setPosition(IPosition position) {
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

    public void clearAnimationEndedCallbacks() {
        this.animationEndedCallbacks.clear();
    }

    public void setAnimationEndedCallback(AnimationEndedCallback callback) {
        clearAnimationEndedCallbacks();
        addAnimationEndedCallback(callback);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setStopCondition(AnimationStopCondition stopCondition) {
        this.setStopCondition(stopCondition, false);
    }

    public void setStopCondition(AnimationStopCondition stopCondition, boolean infiniteTickDuration) {
        this.stopCondition = stopCondition;
        if (infiniteTickDuration) this.setTicksDuration(Integer.MAX_VALUE);
    }

    public AnimationStopCondition getStopCondition() {
        return this.stopCondition;
    }

    public void addCollisionHandler(CollisionHandler<?, AAnimationTask<?>> collisionHandler) {
        this.collisionHandlers.add(collisionHandler);
    }

    public Set<CollisionHandler<?, AAnimationTask<?>>> getCollisionHandlers() {
        return collisionHandlers;
    }

    public IVariable<PARotation> getRotation() {
        return rotation;
    }

    public void setRotation(IVariable<PARotation> rotation) {
        this.rotation = rotation;
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
        obj.position = this.position.copy();
        obj.pointDefinition = this.pointDefinition.copy();
        obj.plugin = this.plugin;
        obj.ticksDuration = this.ticksDuration;
        obj.showPeriod = this.showPeriod.copy();
        obj.animationEndedCallbacks = new HashSet<>(animationEndedCallbacks);
        obj.viewers = this.viewers.clone();
        obj.stopCondition = this.stopCondition;
        obj.collisionHandlers = new HashSet<>(collisionHandlers);
        obj.rotation = this.rotation.copy();
        return obj;
    }
}
