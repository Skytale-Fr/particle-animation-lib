package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationConfig;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.attr.SubAnimOrientationModifier;
import fr.skytale.particleanimlib.animation.attribute.pointdefinition.parent.APointDefinition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class SubAnimPointDefinition implements APointDefinition {

    protected final SubAnimOrientationConfig subAnimationOrientationConfig;
    protected AAnimation subAnimation;

    public SubAnimPointDefinition(AAnimation subAnimation) {
        this(subAnimation, new SubAnimOrientationConfig(SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION));
    }

    public SubAnimPointDefinition(AAnimation subAnimation, SubAnimOrientationConfig subAnimationOrientationConfig) {
        this.subAnimation = subAnimation;
        if (subAnimationOrientationConfig == null) {
            throw new IllegalArgumentException("subAnimationOrientationConfig should not be null");
        }
        this.subAnimationOrientationConfig = subAnimationOrientationConfig;
    }

    public SubAnimPointDefinition(SubAnimPointDefinition subAnimPointDefinition) {
        this.subAnimation = subAnimPointDefinition.getSubAnimation();
        this.subAnimationOrientationConfig = new SubAnimOrientationConfig(subAnimPointDefinition.getSubAnimOrientationConfig());
    }


    public AAnimation getSubAnimation() {
        return subAnimation;
    }

    public SubAnimOrientationConfig getSubAnimOrientationConfig() {
        return subAnimationOrientationConfig;
    }

    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        AAnimation newSubAnimation = subAnimation.clone();
        PARotation additionalRotation = null;
        Vector subAnimationDirection = null;
        switch (subAnimationOrientationConfig.getRotationModifier()) {
            case PARENT_ANIM_CENTER_ORIENTATION:
                additionalRotation = new PARotation(AAnimationTask.W, fromAnimCenterToPoint);
                break;
            case PARENT_ANIM_MOVEMENT_ORIENTATION:
                additionalRotation = new PARotation(AAnimationTask.W, fromPreviousToCurrentAnimBaseLocation);
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

    @Override
    public APointDefinition copy() {
        return new SubAnimPointDefinition(this);
    }
}

