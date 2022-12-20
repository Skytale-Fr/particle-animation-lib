package fr.skytale.particleanimlib.animation.attribute.position.trailposition;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.ATrailPosition;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;

/**
 * To create animations that appears in the trail of a player
 * Those animations will not move
 */
public class StaticTrailPosition extends ATrailPosition {
    public StaticTrailPosition(boolean rotateAnimationToShow) {
        super((animationToShow, player, trailPointData) -> {
            animationToShow.setPosition(new LocatedAnimationPosition(trailPointData.getLocation()));
            if (rotateAnimationToShow) {
                IVariable<PARotation> animationRotation = animationToShow.getRotation();
                PARotation playerMoveRotation = new PARotation(AAnimationTask.W, trailPointData.getVelocity());

                animationRotation = new CallbackVariable<PARotation>(iterationCount -> {
                    PARotation paRotation = new PARotation(animationToShow.getRotation().getCurrentValue(iterationCount));
                    paRotation.rotate(playerMoveRotation);
                    return paRotation;
                });
                animationToShow.setRotation(animationRotation);
            }
        });
    }
}
