package fr.skytale.particleanimlib.trail.attributes;

import fr.skytale.particleanimlib.trail.Trail;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface TrailEndedCallback {
    void run(Trail trail, UUID playerUUID);
}
