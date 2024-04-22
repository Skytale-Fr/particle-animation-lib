package fr.skytale.particleanimlib.animation.attribute.viewers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiPredicate;

public abstract class AViewers implements Cloneable {

    protected final Type type;

    protected AViewers(Type type) {
        this.type = type;
    }

    public static AViewers fromCustomPlayers(Collection<Player> viewers) {
        return new CustomPlayers(viewers);
    }

    public static AViewers fromNearbyPlayers(double distance) {
        return new NearbyPlayers(distance);
    }

    public static AViewers fromPredicateMatchingPlayers(BiPredicate<Player, Location> biPredicate) {
        return new PredicateMatchingPlayers(biPredicate);
    }

    public Type getType() {
        return type;
    }

    public abstract Collection<Player> getPlayers(Location pointLocation);

    @Override
    public AViewers clone() {
        AViewers obj = null;
        try {
            obj = (AViewers) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert obj != null;
        return obj;
    }

    public static enum Type {
        NEARBY_PLAYERS,

        NEARBY_ALIVE_PLAYERS,
        PREDICATE_MATCHING_PLAYERS,
        CUSTOM_PLAYERS;
    }
}
