package fr.skytale.particleanimlib.animation.attribute.position.trailposition;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.TargetingEntityAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.TrailTargetEntityFinder;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.ATrailPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TargetingEntityTrailPosition extends ATrailPosition {

    private final TrailTargetEntityFinder trailTargetEntityFinder;
    private final double speed;

    public TargetingEntityTrailPosition(
            boolean yawRotatedAsPreviousPlayerOrientation,
            boolean pitchRotatedAsPreviousPlayerOrientation,
            double speed,
            Vector originLocationOffset,
            TrailTargetEntityFinder trailTargetEntityFinder
    ) {
        super(yawRotatedAsPreviousPlayerOrientation, pitchRotatedAsPreviousPlayerOrientation, originLocationOffset);
        this.trailTargetEntityFinder = trailTargetEntityFinder;
        this.speed = speed;
    }

    public TargetingEntityTrailPosition(TargetingEntityTrailPosition targetingEntityTrailPosition) {
        super(targetingEntityTrailPosition);
        this.trailTargetEntityFinder = targetingEntityTrailPosition.trailTargetEntityFinder;
        this.speed = targetingEntityTrailPosition.speed;
    }

    @Override
    protected AAnimationPosition buildPositionFromTrailPosition(AAnimation animationToShowCloned, Player player, TrailPlayerLocationData trailPlayerData) {
        Location originLocation = getOriginLocationWithOffset(trailPlayerData);
        final Entity targetedEntity = trailTargetEntityFinder.findTargetedEntity(animationToShowCloned, originLocation, player, trailPlayerData);

        if (targetedEntity == null) {
            return null;
        }

        return new TargetingEntityAnimationPosition(
                originLocation,
                targetedEntity,
                speed
        );
    }


    @Override
    public IPosition copy() {
        return new TargetingEntityTrailPosition(this);
    }
}
