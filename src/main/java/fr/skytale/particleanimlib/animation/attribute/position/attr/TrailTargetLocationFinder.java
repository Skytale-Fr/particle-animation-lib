package fr.skytale.particleanimlib.animation.attribute.position.attr;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface TrailTargetLocationFinder {
    Location findTargetedLocation(AAnimation animationToShowCloned, Location originLocation, Player player, TrailPlayerLocationData trailPlayerData);
}
