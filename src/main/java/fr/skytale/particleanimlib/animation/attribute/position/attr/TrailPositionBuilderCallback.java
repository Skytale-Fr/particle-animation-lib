package fr.skytale.particleanimlib.animation.attribute.position.attr;

import fr.skytale.particleanimlib.animation.parent.animation.AAnimation;
import fr.skytale.particleanimlib.trail.attribute.TrailPlayerLocationData;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface TrailPositionBuilderCallback {

    /**
     * Modify the animation to create in the player trail before it is shown
     *
     * @param player          the player
     * @param trailPlayerData The previous player position where the animation should be shown
     *                        Also contains the velocity that the player had when he was there
     */
    void buildPositionFromTrailPosition(AAnimation animationToShow, Player player, TrailPlayerLocationData trailPlayerData);

}
