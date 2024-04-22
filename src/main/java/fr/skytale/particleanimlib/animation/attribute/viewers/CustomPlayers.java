package fr.skytale.particleanimlib.animation.attribute.viewers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class CustomPlayers extends AViewers {

    private Collection<Player> viewers;

    public CustomPlayers(Collection<Player> viewers) {
        super(Type.CUSTOM_PLAYERS);
        this.viewers = viewers;
    }

    @Override
    public Collection<Player> getPlayers(Location pointLocation) {
        return viewers;
    }

    @Override
    public CustomPlayers clone() {
        CustomPlayers obj = (CustomPlayers) super.clone();
        obj.viewers = new ArrayList<>(viewers);
        return obj;
    }

}
