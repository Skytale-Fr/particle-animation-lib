package fr.skytale.particleanimlib.animation.attribute.viewers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class CustomPlayers extends AViewers {

    private Collection<? extends Player> viewers;

    public CustomPlayers(Collection<? extends Player> viewers) {
        super(Type.CUSTOM_PLAYERS);
        this.viewers = viewers;
    }

    @Override
    public Collection<? extends Player> getPlayers(Location location) {
        return viewers;
    }

    @Override
    public CustomPlayers clone() {
        CustomPlayers obj = (CustomPlayers) super.clone();
        obj.viewers = new ArrayList<>(viewers);
        return obj;
    }

}
