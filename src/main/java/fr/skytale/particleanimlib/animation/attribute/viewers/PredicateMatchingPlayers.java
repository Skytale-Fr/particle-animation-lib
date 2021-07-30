package fr.skytale.particleanimlib.animation.attribute.viewers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class PredicateMatchingPlayers extends AViewers {

    private BiPredicate<Player, Location> biPredicate;

    public PredicateMatchingPlayers(BiPredicate<Player, Location> biPredicate) {
        super(Type.PREDICATE_MATCHING_PLAYERS);
        this.biPredicate = biPredicate;
    }


    @Override
    public Collection<? extends Player> getPlayers(Location location) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(p -> biPredicate.test(p, location) && p.getWorld().equals(location.getWorld()))
                .collect(Collectors.toSet());
    }

    @Override
    public PredicateMatchingPlayers clone() {
        PredicateMatchingPlayers obj = (PredicateMatchingPlayers) super.clone();
        obj.biPredicate = biPredicate;
        return obj;
    }
}
