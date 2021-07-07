package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.animation.parent.AAnimationBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPAnimSample {

    AAnimationBuilder<?> getInitializedBuilder(Player player, JavaPlugin plugin);

    String getName();
}
