package fr.skytale.particleanimlib.testing.samples;

import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import fr.skytale.particleanimlib.testing.BuildersInitializer;
import fr.skytale.particleanimlib.testing.attributes.AnimationType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface IParticleAnimSample {

    AAnimationBuilder<?> getInitializedBuilder(Player player, JavaPlugin plugin);

    AnimationType getType();
}
