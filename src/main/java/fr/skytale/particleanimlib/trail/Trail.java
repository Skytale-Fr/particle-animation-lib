package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.parent.AAnimation;
import fr.skytale.particleanimlib.trail.attributes.TrailEndedCallback;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Trail implements Cloneable {

    private Set<AAnimation> animations;

    private int checkFrequency;

    private double minPlayerToAnimationDistance;

    private double minDistanceBetweenAnimations;

    private Duration duration;

    private TrailEndedCallback callback;

    public Trail() {
        animations = new HashSet<>();
    }

    public TrailTask start() {
        if (animations.size() == 0) {
            throw new IllegalStateException("A trail requires at least 1 trailAnimation in order to be started.");
        }
        return new TrailTask(this);
    }

    @Override
    public Object clone() {
        Trail obj = null;
        try {
            obj = (Trail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        obj.animations = animations.stream()
                .map(animation -> (AAnimation) animation.clone())
                .collect(Collectors.toSet());
        obj.duration = Duration.from(duration);
        return obj;
    }

    /***********GETTERS & SETTERS***********/

    public Set<AAnimation> getAnimations() {
        return animations;
    }

    public void setAnimations(Set<AAnimation> animations) {
        this.animations = animations;
    }

    public void addAnimation(AAnimation animation) {
        this.animations.add(animation);
    }

    public boolean removeAnimation(AAnimation animation) {
        return this.animations.remove(animation);
    }

    public void clearAnimations() {
        this.animations.clear();
    }

    public int getCheckFrequency() {
        return checkFrequency;
    }

    public void setCheckFrequency(int checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    public double getMinPlayerToAnimationDistance() {
        return minPlayerToAnimationDistance;
    }

    public void setMinPlayerToAnimationDistance(double minPlayerToAnimationDistance) {
        this.minPlayerToAnimationDistance = minPlayerToAnimationDistance;
    }

    public double getMinDistanceBetweenAnimations() {
        return minDistanceBetweenAnimations;
    }

    public void setMinDistanceBetweenAnimations(double minDistanceBetweenAnimations) {
        this.minDistanceBetweenAnimations = minDistanceBetweenAnimations;
    }

    public TrailEndedCallback getCallback() {
        return callback;
    }

    public void setCallback(TrailEndedCallback callback) {
        this.callback = callback;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
