package fr.skytale.particleanimlib.animation.attribute.viewers;

import fr.skytale.particleanimlib.animation.attribute.var.Constant;
import fr.skytale.particleanimlib.animation.attribute.var.parent.IVariable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

public class WorldPlayers extends AViewers {

    public WorldPlayers() {
        super(Type.WORLD_PLAYERS);
    }

    @Override
    public Collection<? extends Player> getPlayers(Location location) {
        return Objects.requireNonNull(location.getWorld()).getPlayers();
    }
}
