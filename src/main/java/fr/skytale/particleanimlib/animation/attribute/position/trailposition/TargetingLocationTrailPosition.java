package fr.skytale.particleanimlib.animation.attribute.position.trailposition;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.TargetingLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.TrailTargetLocationFinder;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.ATrailPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TargetingLocationTrailPosition extends ATrailPosition {

    private final TrailTargetLocationFinder trailTargetEntityFinder;

    private final double speed;

    public TargetingLocationTrailPosition(
            boolean yawRotatedAsPreviousPlayerOrientation,
            boolean pitchRotatedAsPreviousPlayerOrientation,
            double speed,
            Vector originLocationOffset,
            TrailTargetLocationFinder trailTargetEntityFinder
    ) {
        super(yawRotatedAsPreviousPlayerOrientation, pitchRotatedAsPreviousPlayerOrientation, originLocationOffset);
        this.trailTargetEntityFinder = trailTargetEntityFinder;
        this.speed = speed;
    }

    public TargetingLocationTrailPosition(TargetingLocationTrailPosition targetingEntityTrailPosition) {
        super(targetingEntityTrailPosition);
        this.trailTargetEntityFinder = targetingEntityTrailPosition.trailTargetEntityFinder;
        this.speed = targetingEntityTrailPosition.speed;
    }

    @Override
    protected AAnimationPosition buildPositionFromTrailPosition(AAnimation animationToShowCloned, Player player, TrailPlayerLocationData trailPlayerData) {
        Location originLocation = getOriginLocationWithOffset(trailPlayerData);
        final Location targetedLocation = trailTargetEntityFinder.findTargetedLocation(animationToShowCloned, originLocation, player, trailPlayerData);

        if (targetedLocation == null) {
            return null;
        }

        return new TargetingLocationAnimationPosition(
                originLocation,
                targetedLocation,
                speed
        );
    }


    @Override
    public IPosition copy() {
        return new TargetingLocationTrailPosition(this);
    }
}
