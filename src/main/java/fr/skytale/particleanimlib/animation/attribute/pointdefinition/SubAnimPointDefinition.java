package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.animation.DirectionalAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * To make each point of the parent animation displaying a sub animation
 */
public class SubAnimPointDefinition implements APointDefinition {

    protected final SubAnimOrientationConfig subAnimationOrientationConfig;
    protected AAnimation subAnimation;

    /**
     * Allows each point of the parent animation to display a sub animation
     * @param subAnimation the sub animation to display
     */
    public SubAnimPointDefinition(AAnimation subAnimation) {
        this(subAnimation, getDefaultSubAnimOrientationConfig(subAnimation));
    }

    /**
     * Allows each point of the parent animation to display a sub animation
     * @param subAnimation the sub animation to display
     * @param subAnimationOrientationConfig how the orientation of the sub animation will be modified according to the
     *                                     parent animation
     */
    public SubAnimPointDefinition(AAnimation subAnimation, SubAnimOrientationConfig subAnimationOrientationConfig) {
        this.subAnimation = subAnimation;
        if (subAnimationOrientationConfig == null) {
            throw new IllegalArgumentException("subAnimationOrientationConfig should not be null");
        }
        this.subAnimationOrientationConfig = subAnimationOrientationConfig;
    }

    /**
     * Copy constructor
     * @param subAnimPointDefinition the SubAnimPointDefinition to copy
     */
    public SubAnimPointDefinition(SubAnimPointDefinition subAnimPointDefinition) {
        this.subAnimation = subAnimPointDefinition.getSubAnimation();
        this.subAnimationOrientationConfig = new SubAnimOrientationConfig(subAnimPointDefinition.getSubAnimOrientationConfig());
    }

    private static SubAnimOrientationConfig getDefaultSubAnimOrientationConfig(AAnimation subAnimation) {
        if (subAnimation instanceof DirectionalAnimation) {
            return new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION, 0.2, SubAnimOrientationModifier.NO_ADDITIONAL_ORIENTATION);
        }
        return new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION);
    }

    /**
     * Retrieves the sub animation
     * @return the sub animation
     */
    public AAnimation getSubAnimation() {
        return subAnimation;
    }

    /**
     * Retrieves how the orientation of the sub animation will be modified according to the parent animation
     */
    public SubAnimOrientationConfig getSubAnimOrientationConfig() {
        return subAnimationOrientationConfig;
    }

    /**
     * Display the sub animation
     * @param pointLocation                         The location where the sub animation will be displayed
     * @param animation                             The parent animation
     * @param task                                  The parent animation task
     * @param fromAnimCenterToPoint                 A vector that goes:
     *                                              - From the parent animation center (its current iteration base location)
     *                                              - To the point to show
     * @param fromPreviousToCurrentAnimBaseLocation A vector that goes:
     *                                              - From the parent animation previous base location (of the last iteration)
     *                                              - To the parent animation current base location (of the current iteration)
     */
    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        AAnimation newSubAnimation = subAnimation.clone();
        PARotation additionalRotation = null;
        Vector subAnimationDirection = null;
        switch (subAnimationOrientationConfig.getRotationModifier()) {
            case PARENT_ANIM_CENTER_ORIENTATION:
                additionalRotation = new PARotation(fromAnimCenterToPoint);
                break;
            case PARENT_ANIM_MOVEMENT_ORIENTATION:
                additionalRotation = new PARotation(fromPreviousToCurrentAnimBaseLocation);
                break;
            case NO_ADDITIONAL_ORIENTATION:
                additionalRotation = null;
                break;
            default:
                throw new NotImplementedException("This SubAnimOrientationModifier is not implemented yet");
        }
        switch (subAnimationOrientationConfig.getDirectionModifier()) {
            case PARENT_ANIM_CENTER_ORIENTATION:
                subAnimationDirection = fromAnimCenterToPoint.clone();
                break;
            case PARENT_ANIM_MOVEMENT_ORIENTATION:
                subAnimationDirection = fromPreviousToCurrentAnimBaseLocation.clone();
                break;
            case NO_ADDITIONAL_ORIENTATION:
                subAnimationDirection = AAnimationTask.W.clone();
                break;
            default:
                throw new NotImplementedException("This SubAnimOrientationModifier is not implemented yet");
        }
        PARotation finalAdditionalRotation = additionalRotation;

        newSubAnimation.setRotation(new CallbackVariable<>(iterationCount -> {
            PARotation rotation = new PARotation(subAnimation.getRotation().getCurrentValue(iterationCount));
            if (finalAdditionalRotation != null) {
                rotation.rotate(new PARotation(finalAdditionalRotation));
            }
            return rotation;
        }));
        newSubAnimation.setPosition(new DirectedLocationAnimationPosition(
                pointLocation,
                subAnimationDirection,
                subAnimationOrientationConfig.getSpeed()));
        newSubAnimation.show().setParentTask(task);
    }

    /**
     * Copy the SubAnimPointDefinition
     * @return the copy
     */
    @Override
    public APointDefinition copy() {
        return new SubAnimPointDefinition(this);
    }
}