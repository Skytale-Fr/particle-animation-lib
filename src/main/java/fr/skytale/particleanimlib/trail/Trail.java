package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailEndedCallback;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Trail implements Cloneable {

    private Set<AAnimation> animations;

    private int checkPeriod;

    private double minPlayerToAnimationDistance;

    private double minDistanceBetweenAnimations;

    private Duration duration;

    private TrailEndedCallback callback;

    public Trail() {
        animations = new HashSet<>();
    }

    public TrailTask getTrailTask() {
        if (animations.size() == 0) {
            throw new IllegalStateException("A trail requires at least 1 trailAnimation in order to be started.");
        }
        return new TrailTask(this);
    }

    @Override
    public Trail clone() {
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

    public void addAnimation(AAnimation animation) {
        if (!animation.getPosition().getType().equals(PositionType.TRAIL)) {
            throw new IllegalArgumentException("During trail execution, the animation position type must be TRAIL. Please define a position that extends ATrailPosition.");
        }
        this.animations.add(animation);
    }

    public boolean removeAnimation(AAnimation animation) {
        return this.animations.remove(animation);
    }

    public void clearAnimations() {
        this.animations.clear();
    }

    public int getCheckPeriod() {
        return checkPeriod;
    }

    public void setCheckPeriod(int checkPeriod) {
        this.checkPeriod = checkPeriod;
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
