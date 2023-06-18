package fr.skytale.particleanimlib.animation.collision.processor;

public enum CollisionTestType {

    /**
     * This collision test type describes a collision test that will happen for
     * every particle of the animation.
     */
    PER_PARTICLE,
    /**
     * This collision test type describes a collision test that will happen once,
     * only for the main position of the animation.
     * For instance, if the animation is a sphere, the location should be the center of the sphere.
     */
    MAIN_PARTICLE

}
