package fr.skytale.particleanimlib.animation.attribute.position.trailposition;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.attr.StaticTrailMoveConfiguration;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.ATrailPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.IPosition;
import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * To create animations that appears in the trail of a player
 */
public class StaticTrailPosition extends ATrailPosition {

    private final StaticTrailMoveConfiguration moveConfig;
    public StaticTrailPosition(
            boolean yawRotatedAsPreviousPlayerOrientation,
            boolean pitchRotatedAsPreviousPlayerOrientation,
            Vector originLocationOffset,
            StaticTrailMoveConfiguration moveConfig) {
        super(yawRotatedAsPreviousPlayerOrientation, pitchRotatedAsPreviousPlayerOrientation, originLocationOffset);
        this.moveConfig = moveConfig;
    }

    public StaticTrailPosition(StaticTrailPosition staticTrailPosition) {
        super(staticTrailPosition);
        this.moveConfig = staticTrailPosition.moveConfig;
    }

    @Override
    protected AAnimationPosition buildPositionFromTrailPosition(
            AAnimation animationToShowCloned,
            Player player,
            TrailPlayerLocationData trailPlayerData
    ) {
        Location originLocation = getOriginLocationWithOffset(trailPlayerData);
        if (moveConfig == null) {
            return new LocatedAnimationPosition(originLocation);
        } else {
            return this.moveConfig.getPosition(originLocation, trailPlayerData);
        }
    }

    @Override
    public IPosition copy() {
        return new StaticTrailPosition(this);
    }
}
