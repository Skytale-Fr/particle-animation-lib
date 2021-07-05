package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attributes.TrailEndedCallback;

import java.time.Duration;
import java.util.Set;

public class TrailBuilder {

    private Trail trail;

    public TrailBuilder() {
        trail = new Trail();
    }


    public void setAnimations(Set<AAnimation> animations) {
        trail.setAnimations(animations);
    }

    public void addAnimation(AAnimation animation) {
        trail.getAnimations().add(animation);
    }

    public void setCheckFrequency(int checkFrequency) {
        trail.setCheckFrequency(checkFrequency);
    }

    public void setMinPlayerToAnimationDistance(double minPlayerToAnimationDistance) {
        trail.setMinPlayerToAnimationDistance(minPlayerToAnimationDistance);
    }

    public void setMinDistanceBetweenAnimations(double minDistanceBetweenAnimations) {
        trail.setMinDistanceBetweenAnimations(minDistanceBetweenAnimations);
    }

    public void setCallback(TrailEndedCallback callback) {
        trail.setCallback(callback);
    }

    public void setDuration(Duration duration) {
        trail.setDuration(duration);
    }

    public Trail getTrail() {
        return this.trail;
    }
}
