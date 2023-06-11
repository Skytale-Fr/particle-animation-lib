package fr.skytale.particleanimlib.animation.attribute.position.attr;

import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.util.Vector;

import java.util.function.Function;

public enum StaticTrailDirectionSourceConfiguration {
    PLAYER_VELOCITY_FROM_LAST_TRAIL_LOCATION(trailPlayerLocationData -> {
        Vector direction = trailPlayerLocationData.getVelocity();
        if (direction.equals(new Vector(0, 0, 0))) {
            direction = trailPlayerLocationData.getEyeDirection().multiply(0.1);
        }
        return direction;
    }),
    PLAYER_EYES_DIRECTION(TrailPlayerLocationData::getEyeDirection);


    private final Function<TrailPlayerLocationData, Vector> directionGetter;

    StaticTrailDirectionSourceConfiguration(Function<TrailPlayerLocationData, Vector> directionGetter) {
        this.directionGetter = directionGetter;
    }

    public Vector getDirection(TrailPlayerLocationData trailPlayerLocationData) {
        return directionGetter.apply(trailPlayerLocationData);
    }
}
