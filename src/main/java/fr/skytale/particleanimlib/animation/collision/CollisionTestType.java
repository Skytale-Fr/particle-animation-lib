package fr.skytale.particleanimlib.animation.collision;

public enum CollisionTestType {

    /**
     * This collision test type describes a collision test that will happen for
     * every particle of the animation.
     */
    PER_PARTICLE,
    /**
     * This collision test type describes a collision test that will happen once,
     * only for the main position of the animation.
     */
    MAIN_PARTICLE

}
