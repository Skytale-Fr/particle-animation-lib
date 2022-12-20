package fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr;

/**
 * Allow to orientate sub animations differently
 */
public enum SubAnimOrientationModifier {
    /**
     * The sub anim orientation will be modified according to a vector that goes:
     * - from the parent animation location
     * - to the sub animation location
     */
    PARENT_ANIM_CENTER_ORIENTATION,
    /**
     * TThe sub anim orientation will be modified according to a vector that goes:
     * - from the parent animation previous iteration location
     * - to the parent animation current iteration location
     */
    PARENT_ANIM_MOVEMENT_ORIENTATION,

    /**
     * The sub anim will not be modified
     */
    NO_ADDITIONAL_ORIENTATION
}
