package fr.skytale.particleanimlib.animation.attribute.position.attr;

import fr.skytale.particleanimlib.animation.attribute.position.animationposition.DirectedLocationAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.animationposition.LocatedAnimationPosition;
import fr.skytale.particleanimlib.animation.attribute.position.parent.AAnimationPosition;
import fr.skytale.particleanimlib.animation.parent.task.AAnimationTask;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StaticTrailMoveConfiguration {
    private final Double forcedSpeed;
    private final StaticTrailDirectionSourceConfiguration source;

    public StaticTrailMoveConfiguration(StaticTrailDirectionSourceConfiguration source) {
        this.source = source;
        this.forcedSpeed = null;
    }

    public StaticTrailMoveConfiguration(StaticTrailDirectionSourceConfiguration source, Double speed) {
        this.forcedSpeed = speed;
        this.source = source;
    }

    public Double getForcedSpeed() {
        return forcedSpeed;
    }

    public StaticTrailDirectionSourceConfiguration getTrailDirectionSource() {
        return source;
    }

    public AAnimationPosition getPosition(Location originLocation, TrailPlayerLocationData trailPlayerData) {
        if (
                (forcedSpeed != null && forcedSpeed == 0) ||
                (source == null && forcedSpeed == null)
        ) {
            return new LocatedAnimationPosition(originLocation);
        }
        Vector direction;
        if (source != null) {
            direction = source.getDirection(trailPlayerData).clone();
        } else {
            direction = AAnimationTask.W.clone();
        }
        if (forcedSpeed != null) {
            return new DirectedLocationAnimationPosition(originLocation, direction, this.forcedSpeed);
        } else {
            return new DirectedLocationAnimationPosition(originLocation, direction, direction.length());
        }
    }
}
