package fr.skytale.particleanimlib.trail.preset.circle;

import fr.skytale.particleanimlib.animation.animation.circle.CircleBuilder;
import fr.skytale.particleanimlib.animation.attribute.position.trailposition.TargetingEntityTrailPosition;
import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.trail.TrailBuilder;
import fr.skytale.particleanimlib.trail.parent.ATrailPresetExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.*;

public class CircleTargetingEntityTrailPresetExecutor extends ATrailPresetExecutor {
    @Override
    public void applyPreset(TrailBuilder trailBuilder, JavaPlugin plugin) {

        CircleBuilder circleBuilder = new CircleBuilder();
        circleBuilder.setJavaPlugin(plugin);
        circleBuilder.setNbPoints(20, true);
        circleBuilder.setRadius(4);
        circleBuilder.setTicksDuration(80);
        circleBuilder.setShowPeriod(new Constant<>(2));
        circleBuilder.setPosition(new TargetingEntityTrailPosition(
                true,
                true,
                1f,
                new Vector(0, 0.9, 0),
                (animationToShowCloned, originLocation, player, trailPlayerData) -> {
                    //find entities in a box of 100 blocks cube around the player
                    final Collection<Entity> nearbyEntities = Objects.requireNonNull(trailPlayerData.getLocation().getWorld()).getNearbyEntities(
                            trailPlayerData.getLocation(),
                            50,
                            50,
                            50
                    );
                    //delete player from nearbyEntities
                    nearbyEntities.removeIf(entity -> entity.getUniqueId().equals(player.getUniqueId()));

                    //compute entity to player distance
                    Map<UUID, Double> distanceToPlayerPerEntity = new HashMap<>();
                    nearbyEntities.forEach(entity -> {
                        distanceToPlayerPerEntity.put(entity.getUniqueId(), entity.getLocation().distance(player.getLocation()));
                    });

                    //Find closest entity uuid
                    UUID closestEntityUUID = distanceToPlayerPerEntity.entrySet().stream()
                            .reduce((uuidDistanceEntry, uuidDistanceEntry2) ->
                                    uuidDistanceEntry.getValue() < uuidDistanceEntry2.getValue() ?
                                            uuidDistanceEntry : uuidDistanceEntry2
                            )
                            .map(Map.Entry::getKey)
                            .orElse(null);

                    //return closest entity or null
                    return closestEntityUUID == null ? null : nearbyEntities.stream()
                            .filter(entity -> entity.getUniqueId().equals(closestEntityUUID))
                            .findAny()
                            .orElse(null);
                }
        ));

        trailBuilder.setDuration(Duration.ofSeconds(200));
        trailBuilder.setCheckPeriod(2);
        trailBuilder.setMinPlayerToAnimationDistance(1);
        trailBuilder.setMinDistanceBetweenAnimations(2);
        trailBuilder.addAnimation(circleBuilder.getAnimation());
    }
}
