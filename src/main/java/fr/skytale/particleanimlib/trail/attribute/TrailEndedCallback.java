package fr.skytale.particleanimlib.trail.attribute;

import fr.skytale.particleanimlib.trail.Trail;

import java.util.UUID;

public interface TrailEndedCallback {
    void run(Trail trail, UUID playerUUID);
}
