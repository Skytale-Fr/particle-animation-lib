package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.attributes.AnimationSample;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface IPAnimSample {

    AAnimationBuilder<?> getInitializedBuilder(Player player, JavaPlugin plugin);

    AnimationSample getType();
}
