package fr.skytale.particleanimlib.animation.attribute.position.parent;

import fr.skytale.particleanimlib.animation.attribute.PARotation;
import fr.skytale.particleanimlib.animation.attribute.position.attr.PositionType;
import fr.skytale.particleanimlib.animation.attribute.var.CallbackVariable;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class ATrailPosition implements IPosition {

    protected final boolean rotateAnimationVerticallyAccordingToPlayerOrientation;

    protected final boolean rotateAnimationHorizontallyAccordingToPlayerOrientation;

    protected final Vector originLocationOffset;


    protected ATrailPosition(
            boolean rotateAnimationVerticallyAccordingToPlayerOrientation,
            boolean rotateAnimationHorizontallyAccordingToPlayerOrientation,
            Vector originLocationOffset) {
        this.rotateAnimationVerticallyAccordingToPlayerOrientation = rotateAnimationVerticallyAccordingToPlayerOrientation;
        this.rotateAnimationHorizontallyAccordingToPlayerOrientation = rotateAnimationHorizontallyAccordingToPlayerOrientation;
        this.originLocationOffset = originLocationOffset;
    }

    protected ATrailPosition(ATrailPosition aTrailPosition) {
        this.rotateAnimationVerticallyAccordingToPlayerOrientation = aTrailPosition.rotateAnimationVerticallyAccordingToPlayerOrientation;
        this.rotateAnimationHorizontallyAccordingToPlayerOrientation = aTrailPosition.rotateAnimationHorizontallyAccordingToPlayerOrientation;
        this.originLocationOffset = aTrailPosition.originLocationOffset;
    }

    public boolean isRotateAnimationVerticallyAccordingToPlayerOrientation() {
        return rotateAnimationVerticallyAccordingToPlayerOrientation;
    }

    public boolean isRotateAnimationHorizontallyAccordingToPlayerOrientation() {
        return rotateAnimationHorizontallyAccordingToPlayerOrientation;
    }

    public Vector getOriginLocationOffset() {
        return originLocationOffset;
    }

    @Override
    public final PositionType getType() {
        return PositionType.TRAIL;
    }

    public final boolean computeFinalPositionAndRotation(AAnimation animationToShow, Player player, TrailPlayerLocationData trailPlayerData) {
        final AAnimationPosition position = buildPositionFromTrailPosition(animationToShow, player, trailPlayerData);
        if (position == null) {
            return false;
        }
        animationToShow.setPosition(position);
        animationToShow.setRotation(rotateUsingPlayerDirection(animationToShow.getRotation(), trailPlayerData));
        return true;
    }

    protected IVariable<PARotation> rotateUsingPlayerDirection(IVariable<PARotation> originRotation, TrailPlayerLocationData trailPlayerLocationData) {
        if (isRotateAnimationHorizontallyAccordingToPlayerOrientation() && isRotateAnimationVerticallyAccordingToPlayerOrientation()) {
            return new CallbackVariable<>(iterationCount -> {
                PARotation result = new PARotation(originRotation.getCurrentValue(iterationCount));
                result.rotate(new PARotation(trailPlayerLocationData.getEyeDirection()));
                return result;
            });
        } else if (isRotateAnimationHorizontallyAccordingToPlayerOrientation()) {
            return new CallbackVariable<>(iterationCount -> {
                PARotation result = new PARotation(originRotation.getCurrentValue(iterationCount));
                final Vector direction = result.rotateVector(AAnimationTask.W);
                result.rotate(new PARotation(new Vector(
                        direction.getX(),
                        trailPlayerLocationData.getEyeDirection().getY(),
                        direction.getZ()
                )));
                return result;
            });
        } else if (isRotateAnimationVerticallyAccordingToPlayerOrientation()) {
            return new CallbackVariable<>(iterationCount -> {
                PARotation result = new PARotation(originRotation.getCurrentValue(iterationCount));
                final Vector direction = result.rotateVector(AAnimationTask.W);
                result.rotate(new PARotation(new Vector(
                        trailPlayerLocationData.getEyeDirection().getX(),
                        direction.getY(),
                        trailPlayerLocationData.getEyeDirection().getZ()
                )));
                return result;
            });
        } else {
            return originRotation;
        }
    }

    protected final Location getOriginLocationWithOffset(TrailPlayerLocationData trailPlayerData) {
        Location originLocation = trailPlayerData.getLocation().clone();
        if (originLocationOffset != null) {
            originLocation.add(originLocationOffset);
        }
        return originLocation;
    }


    protected abstract AAnimationPosition buildPositionFromTrailPosition(AAnimation animationToShowCloned, Player player, TrailPlayerLocationData trailPlayerData);

}
