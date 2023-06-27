package fr.skytale.particleanimlib.trail;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailEndedCallback;

import java.time.Duration;
import java.util.Set;

public class TrailBuilder {

    private final Trail trail;

    public TrailBuilder() {
        trail = new Trail();
    }

    /**
     * Add an animation that will then be displayed behind the players that are have this trail
     * @param animation the animation to add
     */
    public void addAnimation(AAnimation animation) {
        trail.addAnimation(animation);
    }

    /**
     * Retrieves the animations that will be displayed behind the players that have this trail
     * @return the animations that will be displayed behind the players that have this trail
     */
    public Set<AAnimation> getAnimations() {
        return trail.getAnimations();
    }

    /**
     * Defines how often the check that verify if an animation should spawn behind a player is done
     * This is a period so its the delay between two checks in ticks
     * @param checkPeriod the delay between two verifications in ticks.
     *                   This verification will, if successful, spawn an animation behind the player
     */
    public void setCheckPeriod(int checkPeriod) {
        trail.setCheckPeriod(checkPeriod);
    }

    /**
     * Defines the minimum distance between the player and the animation for the animation to spawn
     * @param minPlayerToAnimationDistance the minimum distance between the player and the animation for the animation to spawn
     */
    public void setMinPlayerToAnimationDistance(double minPlayerToAnimationDistance) {
        trail.setMinPlayerToAnimationDistance(minPlayerToAnimationDistance);
    }

    /**
     * Defines the minimum distance between two animations for the animation to spawn
     * @param minDistanceBetweenAnimations the minimum distance between two animations for the animation to spawn
     */
    public void setMinDistanceBetweenAnimations(double minDistanceBetweenAnimations) {
        trail.setMinDistanceBetweenAnimations(minDistanceBetweenAnimations);
    }

    /**
     * Defines a lambda that will be called when the trail ends
     * @param callback the lambda that will be called when the trail ends
     */
    public void setCallback(TrailEndedCallback callback) {
        trail.setCallback(callback);
    }

    /**
     * Defines the duration of the trail
     * @param duration the duration of the trail
     */
    public void setDuration(Duration duration) {
        trail.setDuration(duration);
    }

    /**
     * Retrieves the trail that was built
     * @return the trail that was built
     */
    public Trail getTrail() {
        return this.trail;
    }
}
