package fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;

/**
 * Allows users to configure how sub animations will be shown:
 * - The movement of the sub animation can be modified through directionModifier and speed
 * - The rotation of the sub animation can be modified through rotationModifier
 */
public class SubAnimOrientationConfig {
    /**
     * How the direction of the sub animation is modified according to the parent animation
     */
    private final SubAnimOrientationModifier directionModifier;

    /**
     * How quickly the sub animation moves (can be set to 0 to have a static sub anim)
     */
    protected final IVariable<Double> speed;

    /**
     * How the rotation of the sub animation is modified according to the parent animation
     */
    private final SubAnimOrientationModifier rotationModifier;

    /**
     * configure how sub animations will be shown:
     *  - The movement of the sub animation can be modified through directionModifier and speed
     *  - The rotation of the sub animation can be modified through rotationModifier
     *
     * @param directionModifier defines how the direction of the sub animation is modified according to the parent animation
     * @param speed defines how quickly the sub animation moves (can be set to 0 to have a static sub anim)
     * @param rotationModifier defines how the rotation of the sub animation is modified according to the parent animation
     */
    public SubAnimOrientationConfig(
            SubAnimOrientationModifier directionModifier,
            IVariable<Double> speed,
            SubAnimOrientationModifier rotationModifier) {

        if (directionModifier == null) {
            throw new IllegalArgumentException("directionModifier should not be null");
        }
        this.directionModifier = directionModifier;

        if (speed == null) {
            throw new IllegalArgumentException("speed should not be null");
        }
        this.speed = speed;

        if (rotationModifier == null) {
            throw new IllegalArgumentException("rotationModifier should not be null");
        }
        this.rotationModifier = rotationModifier;
    }

    /**
     * configure how sub animations will be shown:
     *  - The movement of the sub animation can be modified through directionModifier and speed
     *  - The rotation of the sub animation can be modified through rotationModifier
     *
     * @param directionModifier defines how the direction of the sub animation is modified according to the parent animation
     * @param speed defines how quickly the sub animation moves (can be set to 0 to have a static sub anim)
     * @param rotationModifier defines how the rotation of the sub animation is modified according to the parent animation
     */
    public SubAnimOrientationConfig(SubAnimOrientationModifier directionModifier, double speed, SubAnimOrientationModifier rotationModifier) {
        this(directionModifier, new Constant<>(speed), rotationModifier);
    }

    /**
     * configure how sub animations will be shown:
     *  - The movement of the sub animation can be modified through directionModifier
     *  - The rotation of the sub animation can be modified through rotationModifier
     *
     * @param directionModifier defines how the direction of the sub animation is modified according to the parent animation
     * @param rotationModifier defines how the rotation of the sub animation is modified according to the parent animation
     */
    public SubAnimOrientationConfig(SubAnimOrientationModifier directionModifier, SubAnimOrientationModifier rotationModifier) {
        this(directionModifier, new Constant<>(0d), rotationModifier);
    }

    /**
     * configure how sub animations will be shown:
     *  - The movement of the sub animation can be modified through orientationModifier and speed
     *  - The rotation of the sub animation can be modified through orientationModifier
     *
     * @param orientationModifier defines how the direction and the rotation of the sub animation is modified according to the parent animation
     * @param speed defines how quickly the sub animation moves (can be set to 0 to have a static sub anim)
     */
    public SubAnimOrientationConfig(SubAnimOrientationModifier orientationModifier, IVariable<Double> speed) {
        this(orientationModifier, speed, orientationModifier);
    }

    /**
     * configure how sub animations will be shown:
     *  - The movement of the sub animation can be modified through orientationModifier and speed
     *  - The rotation of the sub animation can be modified through orientationModifier
     *
     * @param orientationModifier defines how the direction and the rotation of the sub animation is modified according to the parent animation
     * @param speed defines how quickly the sub animation moves (can be set to 0 to have a static sub anim)
     */
    public SubAnimOrientationConfig(SubAnimOrientationModifier orientationModifier, double speed) {
        this(orientationModifier, new Constant<>(speed), orientationModifier);
    }

    /**
     * configure how sub animations will be shown:
     *  - The movement and the rotation of the sub animation can be modified through orientationModifier
     *
     * @param orientationModifier defines how the direction and the rotation of the sub animation is modified according to the parent animation
     */
    public SubAnimOrientationConfig(SubAnimOrientationModifier orientationModifier) {
        this(orientationModifier, new Constant<>(0d), orientationModifier);
    }

    public SubAnimOrientationConfig(SubAnimOrientationConfig subAnimOrientationConfig) {
        this(subAnimOrientationConfig.directionModifier, subAnimOrientationConfig.speed.copy(), subAnimOrientationConfig.rotationModifier);
    }

    public SubAnimOrientationModifier getDirectionModifier() {
        return directionModifier;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public SubAnimOrientationModifier getRotationModifier() {
        return rotationModifier;
    }
}
