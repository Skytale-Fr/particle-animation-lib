package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailEndedCallback;

import java.time.Duration;
import java.util.Set;

public class TrailBuilder {

    private Trail trail;

    public TrailBuilder() {
        trail = new Trail();
    }

    public void addAnimation(AAnimation animation) {
        trail.addAnimation(animation);
    }

    public Set<AAnimation> getAnimations() {
        return trail.getAnimations();
    }

    public void setCheckPeriod(int checkPeriod) {
        trail.setCheckPeriod(checkPeriod);
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
