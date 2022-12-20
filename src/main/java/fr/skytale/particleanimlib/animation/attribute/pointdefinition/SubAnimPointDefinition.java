package fr.skytale.particleanimlib.animation.attribute.pointdefinition;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
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

    protected final IVariable<Double> speed;
    protected SubAnimOrientationModifier subAnimationOrientationModifier;
    protected AAnimation subAnimation;


    public SubAnimPointDefinition(AAnimation subAnimation) {
        this(subAnimation, SubAnimOrientationModifier.PARENT_ANIM_CENTER_ORIENTATION);
    }

    public SubAnimPointDefinition(AAnimation subAnimation, SubAnimOrientationModifier subAnimationOrientationModifier) {
        this(subAnimation, subAnimationOrientationModifier, 0);
    }

    public SubAnimPointDefinition(AAnimation subAnimation, SubAnimOrientationModifier subAnimationOrientationModifier, double speed) {
        this(subAnimation, subAnimationOrientationModifier, new Constant<>(speed));
    }

    public SubAnimPointDefinition(AAnimation subAnimation, SubAnimOrientationModifier subAnimationOrientationModifier, IVariable<Double> speed) {
        this.subAnimation = subAnimation;
        this.subAnimationOrientationModifier = subAnimationOrientationModifier;
        this.speed = speed;
    }

    public SubAnimPointDefinition(SubAnimPointDefinition subAnimPointDefinition) {
        this.speed = subAnimPointDefinition.getSpeed().copy();
        this.subAnimationOrientationModifier = subAnimPointDefinition.getSubAnimationOrientationModifier();
        this.subAnimation = subAnimPointDefinition.getSubAnimation();
    }


    public AAnimation getSubAnimation() {
        return subAnimation;
    }

    public IVariable<Double> getSpeed() {
        return speed;
    }

    public SubAnimOrientationModifier getSubAnimationOrientationModifier() {
        return subAnimationOrientationModifier;
    }

    @Override
    public void show(Location pointLocation, AAnimation animation, AAnimationTask<?> task, Vector fromAnimCenterToPoint, Vector fromPreviousToCurrentAnimBaseLocation) {
        AAnimation newSubAnimation = subAnimation.clone();
        PARotation additionalRotation = null;
        Vector subAnimationDirection = null;
        if (subAnimationOrientationModifier != null) switch (subAnimationOrientationModifier) {
            case PARENT_ANIM_CENTER_ORIENTATION:
                additionalRotation = new PARotation(AAnimationTask.V, fromAnimCenterToPoint);
                subAnimationDirection = fromAnimCenterToPoint;
                break;
            case PARENT_ANIM_MOVEMENT_ORIENTATION:
                additionalRotation = new PARotation(AAnimationTask.V, fromPreviousToCurrentAnimBaseLocation);
                subAnimationDirection = fromPreviousToCurrentAnimBaseLocation;
                break;
            case NO_ADDITIONAL_ORIENTATION:
                additionalRotation = null;
                subAnimationDirection = AAnimationTask.V;
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
                speed));
        newSubAnimation.show().setParentTask(task);
    }

    @Override
    public APointDefinition copy() {
        return new SubAnimPointDefinition(this);
    }
}

