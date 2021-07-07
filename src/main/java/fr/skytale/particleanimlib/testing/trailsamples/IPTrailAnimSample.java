package fr.skytale.particleanimlib.testing.trailsamples;

import fr.skytale.particleanimlib.trail.TrailTask;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPTrailAnimSample {

    TrailTask getTrailTask(JavaPlugin plugin);

    String getName();
}
