package fr.skytale.particleanimlib.animation.attribute.viewers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class NearbyPlayers extends AViewers {

    private final double maxDistance;

    public NearbyPlayers(double maxDistance) {
        super(Type.NEARBY_PLAYERS);
        this.maxDistance = maxDistance;
    }

    @Override
    public Collection<Player> getPlayers(Location animationPosition) {
        return Objects.requireNonNull(animationPosition.getWorld()).getPlayers()
                .stream()
                .filter(player -> player.getLocation().distance(animationPosition) <= this.maxDistance)
                .collect(Collectors.toSet());
    }

    @Override
    public NearbyPlayers clone() {
        return (NearbyPlayers) super.clone();
    }
}
